package com.bookshop.service;

import com.bookshop.entity.Book;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.order.Order;
import com.bookshop.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiPredicate;

@AllArgsConstructor
@Service
public class OrderService {
    private final CartService cartService;
    private final BookService bookService;

    public Optional<Order> createNewOrder() {
        Cart cart = cartService.getCart();
        if (!validateNumberOfBooksInCart(cart)) {
            //TODO FIND A SOLUTION throw new
        }
        OrderStatus orderStatus = new OrderStatus();
        return orderStatus.createNewOrder();
    }


    private boolean validateNumberOfBooksInCart(Cart cart) {
        BiPredicate<CartItem, BookService> checkNumberOfBooks = ((cartItem, bookService1) -> {
            Long numberOfBooks = cartItem.getAmountOfItems();
            Long bookId = cartItem.getBook().getId();
            Optional<Book> book = bookService1.getBookByID(bookId);
            return book.map(book1 -> book.get().getInSock()).map(books -> numberOfBooks >= books).orElse(false);
        });
        return cart.getCartItems()
                .stream()
                .allMatch(cartItem -> checkNumberOfBooks.test(cartItem, bookService));
    }

}
