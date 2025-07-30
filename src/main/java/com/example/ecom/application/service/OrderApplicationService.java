package com.example.ecom.application.service;

import com.example.ecom.application.inbounds.PlaceOrderData;
import com.example.ecom.application.outbounds.OrderDTO;
import com.example.ecom.domain.model.Order;
import com.example.ecom.domain.model.OrderItem;
import com.example.ecom.domain.model.Product;
import com.example.ecom.domain.port.in.IOrderService;
import com.example.ecom.domain.port.out.ProductRepository;
import com.example.ecom.domain.service.OrderService;
import com.example.ecom.infrastructure.adapters.out.external.payment.PaymentRequest;
import com.example.ecom.infrastructure.shared.exceptions.InsufficientStockException;
import com.example.ecom.infrastructure.shared.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderApplicationService implements IOrderService {
    private final OrderService orderService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderDTO placeOrder(PlaceOrderData data) {
        if (data.userId() == null || data.userId() <= 0) {
            throw new IllegalArgumentException("User ID cannot be null or negative.");
        }
        if (data.items() == null || data.items().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new java.util.ArrayList<>();

        for (PlaceOrderData.OrderItemData itemCommand : data.items()) {
            Product product = productRepository.findById(itemCommand.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID " + itemCommand.productId() + " not found."));

            if (product.getStockQuantity() < itemCommand.quantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName() +
                        ". Available: " + product.getStockQuantity() +
                        ", Requested: " + itemCommand.quantity());
            }

            OrderItem orderItem = OrderItem.createNewOrderItem(
                    itemCommand.productId(),
                    itemCommand.quantity(),
                    product.getPrice()
            );
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemCommand.quantity())));
        }

        Order newOrder = Order.createNewOrder(
                data.userId(),
                totalAmount,
                orderItems
        );

        PaymentRequest paymentRequest = new PaymentRequest(
                newOrder.getTotalAmount().doubleValue(),
                "BRL",
                "some_tokenized_card_data"
        );

        Order placedOrder = orderService.placeOrder(newOrder, paymentRequest);

        return buildOrderDTO(placedOrder);
    }

    private OrderDTO buildOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderDTO.OrderItemDTO> itemDtos = order.getItems().stream()
                .map(item -> {
                    String productName = productRepository.findById(item.getProductId())
                            .map(Product::getName)
                            .orElse("Unknown Product");
                    return OrderDTO.OrderItemDTO.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .priceAtOrder(item.getPriceAtOrder())
                            .build();
                })
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .total(order.getTotalAmount())
                .items(order.getItems().stream().map(orderItem -> OrderDTO.OrderItemDTO.builder()
                        .productId(orderItem.getProductId())
                        .priceAtOrder(orderItem.getPriceAtOrder())
                        .quantity(orderItem.getQuantity())
                        .build()).toList())
                .build();
    }
}
