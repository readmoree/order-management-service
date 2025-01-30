package com.readmoree.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.OrderDao;
import com.readmoree.dao.OrderDetailsDao;
import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDetailsDto;
import com.readmoree.entities.Order;
import com.readmoree.entities.OrderDetails;
import com.readmoree.entities.OrderStatus;
import com.readmoree.exceptions.ResourceNotFoundException;
import com.readmoree.service.OrderDetailsService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
	
	private OrderDetailsDao orderDetailsDao;
	private OrderDao orderDao;
	private ModelMapper modelMapper;
	@Override
	public ApiResponse createOrder(Long userId, Long addressId, List<OrderDetailsDto> orderDetailsDtoList) {
		
		//userId validation
		//addressId validation
		
		//generate orderId
		String OrderId = Order.generateOrderId();
		
		List<OrderDetails> orderDetailsList = orderDetailsDtoList.stream()
		.map(orderDetailDto-> modelMapper.map(orderDetailDto, OrderDetails.class))
		.collect(Collectors.toList());
		
		//order object 
		Order order = new Order();
		order.setOrderId(OrderId);
		order.setCustomerId(userId);
		order.setAddresId(addressId);
		order.setPaymentId((long)(Math.random() * Long.MAX_VALUE));
		order.setTrackingId((long)(Math.random() * Long.MAX_VALUE));
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDetails(orderDetailsList);
		
		Double orderTotal = 0.0;
		for(OrderDetails orderDetail: orderDetailsList) {
			orderDetail.setOrders(order);
			orderTotal += orderDetail.getPrice()*orderDetail.getQuantity();
		}
		order.setOrderTotal(orderTotal);
		
		orderDao.save(order);
		orderDetailsList.forEach((orderDetails)->orderDetailsDao.save(orderDetails));
		
		List<OrderDetailsDto> orderDetailsDtos = orderDetailsList.stream()
		.map(orderDetail->modelMapper.map(orderDetail, OrderDetailsDto.class))
		.collect(Collectors.toList());
		
		return new ApiResponse("Order placed successfully!",orderDetailsDtos);
	}
	
	@Override
	public ApiResponse cancelOrder(Long userId, String orderId) {
		
		//userId validation
		
		//orderId validation
		
		Order order = orderDao.findById(orderId).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		order.setOrderStatus(OrderStatus.CANCELED);
		
		return new ApiResponse("Order with id: "+order.getOrderId()+" has been cancelled successfully!");
	}

	@Override
	public ApiResponse returnOrder(Long userId, String orderId) {
		Order order = orderDao.findById(orderId).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		order.setOrderStatus(OrderStatus.RETURNED);
		
		return new ApiResponse("Return request for order with id: "+order.getOrderId()+" has been accepted!");
		
	}

	@Override
	public ApiResponse exchangeOrder(Long userId, String orderId) {
		Order order = orderDao.findById(orderId).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		order.setOrderStatus(OrderStatus.EXCHANGED);
		
		return new ApiResponse("Exchange request for order with id: "+order.getOrderId()+" has been accepted!");
	}
	

	
	
}
