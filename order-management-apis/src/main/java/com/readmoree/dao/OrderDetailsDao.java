package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.readmoree.entities.OrderDetails;



public interface OrderDetailsDao extends JpaRepository<OrderDetails, Long> {
	
}
