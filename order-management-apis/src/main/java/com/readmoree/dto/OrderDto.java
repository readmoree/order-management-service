package com.readmoree.dto;

import java.time.LocalDateTime;

import com.readmoree.entities.OrderStatus;
import com.readmoree.entities.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
	
	private Long bookId;
	private Integer quantity;
	private Double price;
	private String orderId;
	private Long paymentId;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private Long trackingId;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Double orderTotal;
	private String bookName;
	private Integer customerId;
	private Integer addressId;

	public OrderDto(Integer customerId,Integer addressId,Long bookId,String bookName,Double price,Integer quantity,String orderId,Long trackingId,LocalDateTime orderDate, OrderStatus orderStatus,Double orderTotal  
			, Long paymentId, String paymentMethod, PaymentStatus paymentStatus) {

		this.customerId =customerId;
		this.addressId = addressId;
		this.bookId = bookId;
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.trackingId = trackingId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
	}

	//"od.bookId, od.price, od.quantity, o.orderId, o.trackingId, "+
    //"o.orderDate, o.orderStatus, o.orderTotal, p.paymentId, p.paymentMethod, p.paymentStatus)

	public OrderDto(Long bookId,Double price,Integer quantity,String orderId,Long trackingId,LocalDateTime orderDate, OrderStatus orderStatus,Double orderTotal  
			, Long paymentId, String paymentMethod, PaymentStatus paymentStatus) {
		this.bookId = bookId;
		this.price = price;
		this.quantity = quantity;
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.trackingId = trackingId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
	}


}
