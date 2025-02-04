package com.readmoree.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
public class Order{

	@Id
	private String orderId;

	@Column(name="customer_id")
	private Integer customerId;


	@Column(name="address_id")
	private Integer addresId;

	@Column(name="tracking_id")
	private Long trackingId;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Payment payment;

	@Column(name = "payment_id")
	private Long paymentId; 

	@CreationTimestamp
	@Column(name="order_date")
	private LocalDateTime orderDate;

	@UpdateTimestamp
	@Column(name="updated_on")
	private LocalDateTime updatedOn;

	//order total : calculated from order details
	private Double orderTotal;

	//order status : enum
	@Enumerated(EnumType.STRING)
	@Column(name="order_status", length=20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
	@JsonManagedReference 
	private List<OrderDetails> orderDetails;

	public static String generateOrderId() {
		return UUID.randomUUID().toString();  // Generates a random order ID
	}

}
