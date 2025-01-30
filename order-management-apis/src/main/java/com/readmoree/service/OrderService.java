package com.readmoree.service;

import java.util.List;

import com.readmoree.dto.OrderDto;
import com.readmoree.entities.Order;

public interface OrderService {

	List<OrderDto> getAllOrders(Long userId);

}
