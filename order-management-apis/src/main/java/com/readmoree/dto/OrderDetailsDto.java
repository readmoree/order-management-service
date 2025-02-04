package com.readmoree.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailsDto { 
		
		@NotNull(message="book id can not be null")
		private Long bookId;
		
		@NotNull(message="quantity can not be null")
		private Integer quantity;

		@NotNull(message="price can not be null")
		private Double price;
		
}
