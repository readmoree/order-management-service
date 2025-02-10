package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.readmoree.dto.OrderDto;
import com.readmoree.entities.Order;
import java.util.List;


public interface OrderDao extends JpaRepository<Order, String> {
	List<Order> findByCustomerId(Long customerId);

	@Query("SELECT new com.readmoree.dto.OrderDto(" +
			"od.bookId, od.price, od.quantity, o.orderId, o.addressId, " +
			"o.trackingId, o.orderDate, o.orderStatus, o.orderTotal, " +
			"p.paymentId, p.paymentMethod, p.paymentStatus ) " + // Extract payment fields separately
			"FROM OrderDetails od " +
			"JOIN od.orders o " +
			"LEFT JOIN o.payment p " + // Join payment table
			"WHERE o.customerId = :customerId")
	List<OrderDto> findOrderDetailsByCustomer(@Param("customerId") Integer customerId); 
}
