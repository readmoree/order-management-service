package com.readmoree.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailsDto { 
		
		private Long bookId;
		
		private Integer quantity;

		private Double price;
}
