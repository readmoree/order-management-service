package com.readmoree.service;

import java.util.List;

import com.readmoree.dto.OrderDto;


public interface OrderService {

	List<OrderDto> getAllOrders(Long userId);

}
