package com.bookshop.service;

import com.bookshop.entity.Book;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.order.Order;
import com.bookshop.entity.order.OrderStatus;
import com.bookshop.exceptions.InvalidCartEx;
import com.bookshop.exceptions.OrderNotFoundEx;
import com.bookshop.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiPredicate;

@AllArgsConstructor
@Service
public class OrderService {
    private final CartService cartService;
    private final BookService bookService;
    private final OrderStatusRepository orderStatusRepository;

    public Optional<Order> createNewOrder() {
        Cart cart = cartService.getCart();
        if (!validateNumberOfBooksInCart(cart)) {
            throw new InvalidCartEx("This amount of items is too big: " + cart.getUsername());
        }
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.payForOrder();
        orderStatus.finishOrder();
        orderStatusRepository.save(orderStatus);
        return Optional.empty();
    }

    public Optional<Order> payForOrder(Long id) {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElseThrow(() -> new OrderNotFoundEx("Order was not found: " + id));
        orderStatus.payForOrder();
        return Optional.empty();
    }

    public Optional<Order> finishOrder(Long id) {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElseThrow(() -> new OrderNotFoundEx("Order was not found: " + id));
        orderStatus.finishOrder();
        return Optional.empty();
    }


    private boolean validateNumberOfBooksInCart(Cart cart) {
        BiPredicate<CartItem, BookService> checkNumberOfBooks = ((cartItem, bookService1) -> {
            Long numberInCart = cartItem.getAmountOfItems();
            Long bookId = cartItem.getBook().getId();
            Optional<Book> book = bookService1.getBookByID(bookId);
            return book.map(book1 -> book.get().getInSock()).map(inStock -> numberInCart <= inStock).orElse(false);
        });
        return cart.getCartItems()
                .stream()
                .allMatch(cartItem -> checkNumberOfBooks.test(cartItem, bookService));
    }

}
