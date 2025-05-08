package com.codewithmosh.store.payments;

import com.codewithmosh.store.orders.OrderRepository;
import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.carts.CartIsEmptyException;
import com.codewithmosh.store.carts.CartNotFoundException;
import com.codewithmosh.store.orders.OrderNotFoundException;
import com.codewithmosh.store.carts.CartRepository;
import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) throws PaymentException {
        var cart = cartRepository.getCartWithItems(request.getCartId())
            .orElseThrow(CartNotFoundException::new);

        if (cart.isEmpty()) {
            throw new CartIsEmptyException();
        }

        var user = authService.getCurrentUser();

        var order = Order.fromCart(cart, user);

        orderRepository.save(order);

        try {
            // Create a checkout session
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart);

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        var paymentResult = paymentGateway.parseWebhookRequest(request)
            .orElseThrow(() -> new PaymentException("Invalid webhook event"));

        var order = orderRepository.findById(paymentResult.orderId())
            .orElseThrow(OrderNotFoundException::new);
        order.updateStatus(paymentResult.paymentStatus());
        orderRepository.save(order);
    }
}
