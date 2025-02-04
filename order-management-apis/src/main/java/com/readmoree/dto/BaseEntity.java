package com.readmoree.dto;

import java.time.LocalDateTime;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {
	
	private Long id;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

}
