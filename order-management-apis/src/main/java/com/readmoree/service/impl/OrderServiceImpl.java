package com.readmoree.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.OrderDao;
import com.readmoree.dao.OrderDetailsDao;
import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.OrderDto;
import com.readmoree.entities.Order;
import com.readmoree.entities.OrderDetails;
import com.readmoree.entities.OrderStatus;
import com.readmoree.entities.PaymentStatus;
import com.readmoree.exceptions.ResourceNotFoundException;
import com.readmoree.service.OrderService;
import com.readmoree.utils.RequestUtils;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;
	private OrderDetailsDao orderDetailsDao;

	@Override
	public List<OrderDto> getAllOrders(Integer userId) {

		List<OrderDto> orderDetailsByCustomer = orderDao.findOrderDetailsByCustomer(userId);

		return orderDetailsByCustomer;
	}

	@Override
	public Map<String, List<OrderDto>> getAllOrders() {

		List<Order> allOrders = orderDao.findAll();
		List<OrderDto> orderDtos = new ArrayList<>();

		for(Order order: allOrders) {
			List<OrderDetails> orderDetails = orderDetailsDao.findByOrders(order);

			Map<Long, String> bookNames = new HashMap<>();
			for (OrderDetails orderDetail : orderDetails) {
				Long bookId = orderDetail.getBookId();
				
				// Only make the request if the bookName is not cached
				bookNames.computeIfAbsent(bookId, bookIdKey -> RequestUtils.requestBookService(bookIdKey));
			}	
			
			for (OrderDetails orderDetail : orderDetails) {
	            String bookName = bookNames.get(orderDetail.getBookId());
	            Order orderr = orderDetail.getOrders();      
//	        	orderDtos.add(new OrderDto(bookName, orderDetail.getPrice(), orderDetail.getQuantity(), orderr.getOrderId(), orderr.getTrackingId(), 
//						orderr.getOrderDate(), orderr.getOrderStatus(), orderr.getOrderTotal(), orderr.getPayment().getPaymentId(), orderr.getPayment().getPaymentMethod(), orderr.getPayment().getPaymentStatus()));	        			
	        	orderDtos.add(new OrderDto(order.getCustomerId(), order.getAddresId(),orderDetail.getBookId(),bookName, orderDetail.getPrice(), orderDetail.getQuantity(), orderr.getOrderId(), orderr.getTrackingId(), 
	        			orderr.getOrderDate(), orderr.getOrderStatus(), orderr.getOrderTotal(), 123L , "COD", PaymentStatus.PENDING));	
			}
		}
		return orderDtos.stream().collect(Collectors.groupingBy(OrderDto::getOrderId));
	}
	

	@Override
	public ApiResponse updateOrderStatus(String orderId, OrderStatus orderStatus) {
		orderId = orderId.replace("\"", "").trim();
		Order order = orderDao.findById(orderId.trim()).orElseThrow(()-> new ResourceNotFoundException("invalid order id"));
		order.setOrderStatus(orderStatus);
		
		return new ApiResponse("Order status for order id: "+order.getOrderId()+" updated to: "+orderStatus);
	}

}
