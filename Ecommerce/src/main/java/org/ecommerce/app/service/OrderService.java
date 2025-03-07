package org.ecommerce.app.service;

import org.ecommerce.app.payload.order.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String paymentGatewayName, String paymentGatewayId, String paymentGatewayStatus, String paymentGatewayResponseMessage);
}
