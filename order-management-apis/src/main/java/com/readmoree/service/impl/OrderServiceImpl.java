package com.readmoree.service.impl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.OrderDao;
import com.readmoree.dao.OrderDetailsDao;
import com.readmoree.dto.ApiResponse;
import com.readmoree.dto.BookResponseDto;
import com.readmoree.dto.OrderDto;
import com.readmoree.entities.Order;
import com.readmoree.entities.OrderDetails;
import com.readmoree.entities.OrderStatus;
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
		for(OrderDto orderDto: orderDetailsByCustomer) {
			Long bookId = orderDto.getBookId();
			System.out.println("Book id: "+ bookId);
			BookResponseDto book = RequestUtils.requestBookService(bookId);
			orderDto.setBookName(book.getTitle());
			orderDto.setImage(book.getImage());
			orderDto.setCustomerId(userId);
		}

		return orderDetailsByCustomer;
	}

	@Override
	public Map<String, List<OrderDto>> getAllOrders() {

		List<Order> allOrders = orderDao.findAll();
		Set<OrderDto> orderDtos = new HashSet<>();

		for(Order order: allOrders) {
			List<OrderDetails> orderDetails = orderDetailsDao.findByOrders(order);

			Map<Long, BookResponseDto> books = new HashMap<>();
			for (OrderDetails orderDetail : orderDetails) {
				Long bookId = orderDetail.getBookId();
				
				// Only make the request if the bookName is not cached
				books.computeIfAbsent(bookId, bookIdKey -> RequestUtils.requestBookService(bookIdKey));
			}	
			for (OrderDetails orderDetail : orderDetails) {
	            BookResponseDto book = books.get(orderDetail.getBookId());
	            Order orderr = orderDetail.getOrders();      
	        	orderDtos.add(new OrderDto(order.getCustomerId(), order.getAddressId(),orderDetail.getBookId(),book.getTitle(), orderDetail.getPrice(), orderDetail.getQuantity(), orderr.getOrderId(), orderr.getTrackingId(), 
						orderr.getOrderDate(), orderr.getOrderStatus(), orderr.getOrderTotal(), orderr.getPayment().getPaymentId(), orderr.getPayment().getPaymentMethod(), orderr.getPayment().getPaymentStatus()));	        			
			}
		}
		return orderDtos.stream().collect(Collectors.groupingBy(OrderDto::getOrderId));
	}
	

	@Override
	public ApiResponse updateOrderStatus(String orderId, OrderStatus orderStatus) {
		System.out.println(orderId);
		orderId = orderId.replace("\"", "").trim();
		System.out.println(orderId);
		Order order = orderDao.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("invalid order id"));
		order.setOrderStatus(orderStatus);
		
		return new ApiResponse("Order status for order id: "+order.getOrderId()+" updated to: "+orderStatus,order.getOrderId());
	}

}
