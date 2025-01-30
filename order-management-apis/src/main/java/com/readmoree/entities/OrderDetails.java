package com.readmoree.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetails {
	
	//orderDetails id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//orderId: ManyToOne: Many books can belong to one order
	@ManyToOne
	@JoinColumn(name="order_id",nullable=false)
	private Order orders;
	
	//bookid: ManyToOne(to establish one to one)
//	@ManyToOne
//	@JoinColumn(name="book_id")
//	private Book book;
	private Long bookId;
	
	//quantity
	@Column(name="quantity")
	private Integer quantity;
	
	@CreationTimestamp
	@Column(name="created_on")
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	@Column(name="updated_on")
	private LocalDateTime updatedOn;
	
	//price -> from bookid price can be fetched!
	private Double price;
	

}
