package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmoree.entities.Payment;

public interface PaymentDao extends JpaRepository<Payment, Long> {

}
