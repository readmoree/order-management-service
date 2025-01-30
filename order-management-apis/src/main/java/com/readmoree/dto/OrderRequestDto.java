package com.readmoree.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
	
	private Long customerId;
	
	private Long addressId;
	
	private Long bookId;
	
	private Integer quantity;
	
	private Double price;

}
