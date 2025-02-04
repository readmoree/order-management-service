package com.readmoree.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
	
	@NotNull(message="customer id must not be null")
	private Long customerId;
	
	@NotNull(message="address id must not be null")
	private Long addressId;
	
	@NotNull(message="book id must not be null")
	private Long bookId;
	
	@NotNull(message="quantity must not be null")
	private Integer quantity;
	
	@NotNull(message="price must not be null")
	private Double price;


}
