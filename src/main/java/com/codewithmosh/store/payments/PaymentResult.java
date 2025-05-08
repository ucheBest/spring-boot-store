package com.codewithmosh.store.payments;

import com.codewithmosh.store.entities.PaymentStatus;

public record PaymentResult(Long orderId, PaymentStatus paymentStatus) {
}
