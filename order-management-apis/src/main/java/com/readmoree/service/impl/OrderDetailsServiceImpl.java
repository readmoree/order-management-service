package com.readmoree.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.OrderDao;
import com.readmoree.dao.OrderDetailsDao;
import com.readmoree.dao.PaymentDao;
import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDetailsDto;
import com.readmoree.entities.Order;
import com.readmoree.entities.OrderDetails;
import com.readmoree.entities.OrderStatus;
import com.readmoree.entities.Payment;

import com.readmoree.exceptions.ResourceNotFoundException;
import com.readmoree.service.OrderDetailsService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

	private OrderDetailsDao orderDetailsDao;
	private OrderDao orderDao;
	private PaymentDao paymentDao;
	private ModelMapper modelMapper;
	
	@Override
	public ApiResponse createOrder(Integer userId, Integer addressId,String paymentMethod, List<OrderDetailsDto> orderDetailsDtoList) {

		//generate orderId
		String OrderId = Order.generateOrderId();

		List<OrderDetails> orderDetailsList = orderDetailsDtoList.stream()
				.map(orderDetailDto-> modelMapper.map(orderDetailDto, OrderDetails.class))
				.collect(Collectors.toList());

		//order object 
		Order order = new Order();
		Long paymentId = Payment.generatePaymentId();
		Payment payment = new Payment(paymentId, paymentMethod, Payment.assignPaymentStatus(paymentMethod,paymentId));
		
		
		order.setOrderId(OrderId);
		order.setCustomerId(userId);
		order.setAddresId(addressId);
	
		order.setTrackingId((long)(Math.random() * Long.MAX_VALUE));
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDetails(orderDetailsList);
		
		
		Double orderTotal = 0.0;
		for(OrderDetails orderDetail: orderDetailsList) {
			orderDetail.setOrders(order);
			orderTotal += orderDetail.getPrice()*orderDetail.getQuantity();
		}
		order.setOrderTotal(orderTotal);

		payment.setOrder(order);
		order.setPayment(payment);
		
		order.setPaymentId(paymentId);
		
		orderDao.save(order);
		paymentDao.save(payment);
		
		orderDetailsList.forEach((orderDetails)->orderDetailsDao.save(orderDetails));
		
		

		List<OrderDetailsDto> orderDetailsDtos = orderDetailsList.stream()
				.map(orderDetail->modelMapper.map(orderDetail, OrderDetailsDto.class))
				.collect(Collectors.toList());

		return new ApiResponse("Order placed successfully!",orderDetailsDtos);
	}

	@Override
	public ApiResponse cancelOrder(Integer userId, String orderId) {
		
		//orderId validation
		orderId = orderId.replace("\"", "").trim();
		Order order = orderDao.findById(orderId.trim()).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		
		if(order.getOrderStatus() == OrderStatus.PENDING || order.getOrderStatus() ==OrderStatus.PROCESSING || order.getOrderStatus()==OrderStatus.SHIPPED) {
			order.setOrderStatus(OrderStatus.CANCELED);
			List<OrderDetails> orderDetails = orderDetailsDao.findByOrders(order);

			List<OrderDetailsDto> orderDetailsDto = orderDetails.stream()
					.map(orderDetail->modelMapper.map(orderDetail, OrderDetailsDto.class))
					.collect(Collectors.toList());

			return new ApiResponse("Order with id: "+order.getOrderId()+" has been cancelled successfully!", orderDetailsDto);
		}else {
			return new ApiResponse("Order cannot be cancelled!!");
		}
	}

	@Override
	public ApiResponse returnOrder(Integer userId, String orderId) {

		orderId = orderId.replace("\"", "").trim();
		Order order = orderDao.findById(orderId).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		
		if(order.getOrderStatus() == OrderStatus.DELIVERED) {
			order.setOrderStatus(OrderStatus.RETURNED);
			
			List<OrderDetails> orderDetails = orderDetailsDao.findByOrders(order);

			List<OrderDetailsDto> orderDetailsDto = orderDetails.stream()
					.map(orderDetail->modelMapper.map(orderDetail, OrderDetailsDto.class))
					.collect(Collectors.toList());

			return new ApiResponse("Return request for order with id: "+order.getOrderId()+" has been accepted!",orderDetailsDto);
		}
		else {
			return new ApiResponse("Order could not be returned!");
		}	
	}

	@Override
	public ApiResponse exchangeOrder(Integer userId, String orderId) {

		orderId = orderId.replace("\"", "").trim();
		Order order = orderDao.findById(orderId).orElseThrow(()->new ResourceNotFoundException("invalid order id"));
		if(order.getOrderStatus() == OrderStatus.DELIVERED) {
			
			order.setOrderStatus(OrderStatus.EXCHANGED);

			List<OrderDetails> orderDetails = orderDetailsDao.findByOrders(order);

			List<OrderDetailsDto> orderDetailsDto = orderDetails.stream()
					.map(orderDetail->modelMapper.map(orderDetail, OrderDetailsDto.class))
					.collect(Collectors.toList());

			return new ApiResponse("Exchange request for order with id: "+order.getOrderId()+" has been accepted!",orderDetailsDto);
		}else {
			return new ApiResponse("Order could not be exchanged!");
		}
	}

}
