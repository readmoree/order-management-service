package com.readmoree.service;

import java.util.List;

import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDetailsDto;


public interface OrderDetailsService {

	ApiResponse createOrder(Long userId, Long addressId, List<OrderDetailsDto> orderDetailsDtoList);

	ApiResponse cancelOrder(Long userId, String orderId);

	ApiResponse returnOrder(Long userId, String orderId);

	ApiResponse exchangeOrder(Long userId, String orderId);

//	ApiResponse createOrder(OrderRequestDto orderRequestDto);
	

}
