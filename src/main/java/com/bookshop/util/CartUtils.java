package com.bookshop.util;

import com.bookshop.controller.dto.CartItemRequest;
import com.bookshop.entity.Book;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import com.bookshop.exceptions.BookNotFoundException;
import com.bookshop.service.BookService;

import java.math.BigDecimal;
import java.util.LinkedList;

public class CartUtils {
    public static CartItem buildCartItem(BookService bookService, CartItemRequest cartItemRequest) {
        Book book = bookService.getBookByID(cartItemRequest.getBooksId()).orElseThrow(() -> new BookNotFoundException("Book not found"));
        BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getAmountOfItems()));
        return CartItem.builder()
                .amountOfItems(cartItemRequest.getAmountOfItems())
                .book(book)
                .subTotal(price)
                .build();
    }

    public static Cart createNewCart(String username) {
        return Cart.builder()
                .additionalServices(new LinkedList<>())
                .cartItems(new LinkedList<>())
                .total(BigDecimal.ZERO)
                .username(username)
                .build();
    }
}
