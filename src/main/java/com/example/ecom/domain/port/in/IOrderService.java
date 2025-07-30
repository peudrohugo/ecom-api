package com.example.ecom.domain.port.in;

import com.example.ecom.application.inbounds.PlaceOrderData;
import com.example.ecom.application.outbounds.OrderDTO;

public interface IOrderService {
    OrderDTO placeOrder(PlaceOrderData data);
}
