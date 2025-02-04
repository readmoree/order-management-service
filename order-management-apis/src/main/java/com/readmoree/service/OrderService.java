package com.readmoree.service;

import java.util.List;
import java.util.Map;

import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDto;
import com.readmoree.entities.OrderStatus;


public interface OrderService {

	List<OrderDto> getAllOrders(Integer userId);

	Map<String, List<OrderDto>> getAllOrders();

	ApiResponse updateOrderStatus(String orderId, OrderStatus orderStatus);

}
