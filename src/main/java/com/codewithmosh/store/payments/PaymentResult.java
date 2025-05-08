package com.codewithmosh.store.payments;

public record PaymentResult(Long orderId, PaymentStatus paymentStatus) {
}
