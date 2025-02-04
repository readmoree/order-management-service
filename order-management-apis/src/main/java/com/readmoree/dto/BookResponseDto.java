package com.readmoree.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto extends BaseEntity {
	
	private String image;
	
	private String isbn;
	
	private String title;
	
}
