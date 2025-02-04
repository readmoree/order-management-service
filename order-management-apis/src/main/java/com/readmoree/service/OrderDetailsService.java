package com.readmoree.service;

import java.util.List;

import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDetailsDto;


public interface OrderDetailsService {

	ApiResponse createOrder(Integer userId, Integer addressId,String paymentMethod, List<OrderDetailsDto> orderDetailsDtoList);

	ApiResponse cancelOrder(Integer userId, String orderId);

	ApiResponse returnOrder(Integer userId, String orderId);

	ApiResponse exchangeOrder(Integer userId, String orderId);
}
