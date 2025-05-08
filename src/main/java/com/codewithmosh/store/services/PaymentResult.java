package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.PaymentStatus;

public record PaymentResult(Long orderId, PaymentStatus paymentStatus) {
}
