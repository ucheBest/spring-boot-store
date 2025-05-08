package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}