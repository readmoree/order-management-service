package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmoree.entities.Order;
import com.readmoree.entities.OrderDetails;
import java.util.List;

public interface OrderDetailsDao extends JpaRepository<OrderDetails, Long> {
	
	List<OrderDetails> findByOrders(Order orders);
	
}
