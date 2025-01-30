package com.readmoree.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	//orderid--> randome generation
	@Id
	private String orderId;
	
	//customerid -> api call 
//	@ManyToOne
//	private Customer customer;
	@Column(name="customer_id")
	private Long customerId;
	
	//addressid -> api call
//	@ManyToOne
//	private Address address;
	@Column(name="address_id")
	private Long addresId;
	
	//trackingid -> api call
//	@ManyToOne
//	private Delivery tracking;
	@Column(name="tracking_id")
	private Long trackingId;
	
	//paymentid -> api call
//	@OneToOne
//	@JoinColumn(name="payment_id")
//	private Payment payment;
	private Long paymentId;
	
	//orderDate-->createdOn from BaseEntity
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
	
	@OneToMany(mappedBy = "orders")
    private List<OrderDetails> orderDetails;
	
	public static String generateOrderId() {
	        return UUID.randomUUID().toString();  // Generates a random order ID
	    }

}
