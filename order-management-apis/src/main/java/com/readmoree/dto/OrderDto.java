package com.readmoree.dto;

import java.time.LocalDateTime;

import com.readmoree.entities.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

	//orderDetails Field:
	private Long bookId;
	private Integer quantity;
	private Double price;
	private String orderId;
	private Long paymentId;
	private Long trackingId;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Double orderTotal;
	public OrderDto(Long bookId, Double price, Integer quantity, String orderId, Long paymentId, Long trackingId,
			LocalDateTime orderDate, OrderStatus orderStatus, Double orderTotal) {
		super();
		this.bookId = bookId;
		this.quantity = quantity;
		this.price = price;
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.trackingId = trackingId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
	}
	
}
