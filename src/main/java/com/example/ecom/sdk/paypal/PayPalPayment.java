package com.example.ecom.sdk.paypal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
public class PayPalPayment {
    public String id;
    public String state;
    public String type;
    public BigDecimal total;
    public String currency;
    public LocalDateTime created_time;
    public LocalDateTime updated_time;

}
