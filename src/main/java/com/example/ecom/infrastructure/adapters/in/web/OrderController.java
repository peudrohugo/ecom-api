package com.example.ecom.infrastructure.adapters.in.web;

import com.example.ecom.application.inbounds.PlaceOrderData;
import com.example.ecom.application.outbounds.OrderDTO;
import com.example.ecom.domain.port.in.IOrderService;
import com.example.ecom.infrastructure.shared.exceptions.InsufficientStockException;
import com.example.ecom.infrastructure.shared.exceptions.PaymentException;
import com.example.ecom.infrastructure.shared.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderData data) {
        try {
            OrderDTO placedOrder = orderService.placeOrder(data);
            return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InsufficientStockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (PaymentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
