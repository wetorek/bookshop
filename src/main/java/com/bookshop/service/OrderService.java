package com.bookshop.service;

import com.bookshop.entity.Book;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.order.Order;
import com.bookshop.entity.order.OrderStatus;
import com.bookshop.exceptions.InvalidCartEx;
import com.bookshop.exceptions.OrderNotFoundEx;
import com.bookshop.repository.OrderRepository;
import com.bookshop.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiPredicate;

@AllArgsConstructor
@Service
public class OrderService {
    private final CartService cartService;
    private final BookService bookService;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createNewOrder() {
        Cart cart = cartService.getCart();
        if (!validateNumberOfBooksInCart(cart)) {
            throw new InvalidCartEx("This amount of items is too big: " + cart.getUsername());
        }
        /*Order order = Order.builder()
                .additionalServices(cart.getAdditionalServices())
                .cartItems(cart.getCartItems())
                .total(cart.getTotal())
                .username(cart.getUsername())
                .build();
        cartService.emptyCart();
        orderRepository.save(order);*/
        OrderStatus orderStatus = new OrderStatus(cartService);
        Order order = orderStatus.getOrder();
        orderStatusRepository.save(orderStatus);

        return order;
    }

    public Order payForOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundEx("Order was not found: " + id));
        OrderStatus orderStatus = orderStatusRepository.findByOrder(order).orElseThrow(() -> new OrderNotFoundEx("Order status was not found by this order: " + id));
        orderStatus.payForOrder();
        orderStatusRepository.save(orderStatus);
        return orderStatus.getOrder();
    }

    public Order finishOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundEx("Order was not found: " + id));
        OrderStatus orderStatus = orderStatusRepository.findByOrder(order).orElseThrow(() -> new OrderNotFoundEx("Order status was not found by this order: " + id));
        orderStatus.finishOrder();
        orderStatusRepository.save(orderStatus);
        return orderStatus.getOrder();
    }


    private boolean validateNumberOfBooksInCart(Cart cart) {
        BiPredicate<CartItem, BookService> checkNumberOfBooks = ((cartItem, bookService1) -> {
            Long numberInCart = cartItem.getAmountOfItems();
            Long bookId = cartItem.getBook().getId();
            Book book = bookService1.getBookById(bookId);
            return numberInCart <= book.getInSock();
        });
        return cart.getCartItems()
                .stream()
                .allMatch(cartItem -> checkNumberOfBooks.test(cartItem, bookService));
    }

}
