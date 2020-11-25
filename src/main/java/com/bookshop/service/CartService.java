package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.controller.dto.CartItemRequest;
import com.bookshop.entity.*;
import com.bookshop.exceptions.AdditionalServiceConflictEx;
import com.bookshop.exceptions.AdditionalServiceNotFoundEx;
import com.bookshop.exceptions.BookConflictEx;
import com.bookshop.exceptions.BookNotFoundEx;
import com.bookshop.repository.CartRepository;
import com.bookshop.util.CartUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final BookService bookService;
    private final AdditionalServicesService additionalServicesService;

    public Cart getCart() {
        User user = authService.getCurrentUser();
        Optional<Cart> cartOptional = cartRepository.findCartByUsername(user.getUsername());
        return cartOptional.orElseGet(() -> {
            log.info("new cart created: " + user.getUsername());
            Cart tempCart = CartUtils.createNewCart(user.getUsername());
            cartRepository.save(tempCart);
            return tempCart;
        });
    }

    public Cart addItemToCart(CartItemRequest cartItemRequest) {
        if (bookService.getBookByID(cartItemRequest.getBooksId()).isEmpty()) {
            throw new BookNotFoundEx(cartItemRequest.toString());
        }
        CartItem cartItem = CartUtils.buildCartItem(bookService, cartItemRequest);
        if (cartItem.getAmountOfItems() > cartItem.getBook().getInSock()) {
            throw new BookConflictEx("Selected amount of stock is too big " + cartItemRequest);
        }
        Cart cart = getCart();
        addCartItemToCart(cart, cartItem);
        cartRepository.save(cart);
        return cart;
    }

    private void addCartItemToCart(Cart cart, CartItem cartItem) { //strategy
        if (!doesCartContainProduct(cart, cartItem)) {
            cart.getCartItems().add(cartItem);
        } else {
            CartItem cartItemFromCart = getCartItemFromCartByBookId(cart, cartItem.getBook().getId()).orElseThrow(() -> new BookNotFoundEx("Unexpected error occupied"));
            cartItemFromCart.setAmountOfItems(cartItemFromCart.getAmountOfItems() + cartItem.getAmountOfItems());
            cartItemFromCart.setSubTotal(cartItemFromCart.getSubTotal().add(cartItem.getSubTotal()));
        }
        cart.setTotal(cart.getTotal().add(cartItem.getSubTotal()));
    }

    private Optional<CartItem> getCartItemFromCartByBookId(Cart cart, Long id) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(id))
                .findFirst();
    }

    public Cart removeItemFromCart(CartItemRequest cartItemRequest) {
        Cart cart = getCart();
        if (!doesCartContainProduct(cart, cartItemRequest)) {
            throw new BookNotFoundEx("Book not found " + cartItemRequest);
        }
        CartItem cartItem = getCartItemFromCartByBookId(cart, cartItemRequest.getBooksId()).orElseThrow(() -> new BookNotFoundEx("Unexpected error occupied."));
        if (cartItem.getAmountOfItems() < cartItemRequest.getAmountOfItems()) {
            throw new BookConflictEx("Removed number of product is bigger than current amount in cart " + cartItemRequest);
        }
        cart.getCartItems().remove(cartItem);
        cart.setTotal(cart.getTotal().subtract(cartItem.getSubTotal()));
        cartRepository.save(cart);
        return cart;
    }

    private boolean doesCartContainProduct(Cart cart, CartItemRequest cartItemRequest) { //TODO design pattern
        return cart.getCartItems().stream()
                .map(CartItem::getBook)
                .map(Book::getId)
                .anyMatch(id -> id.equals(cartItemRequest.getBooksId()));
    }

    private boolean doesCartContainProduct(Cart cart, CartItem cartItem) {
        return cart.getCartItems().stream()
                .map(CartItem::getBook)
                .map(Book::getId)
                .anyMatch(id -> id.equals(cartItem.getBook().getId()));
    }


    public Cart addAdditionalService(AdditionalServiceDto additionalServiceDto) {
        System.out.println(additionalServiceDto);
        AdditionalService additionalService = additionalServicesService.getById(additionalServiceDto.getId()).orElseThrow(() ->
                new AdditionalServiceNotFoundEx("Additional Service not found " + additionalServiceDto));
        Cart cart = getCart();
        if (cart.getAdditionalServices().contains(additionalService)) {
            throw new AdditionalServiceConflictEx("This additional service is already in cart! " + additionalServiceDto);
        }
        cart.getAdditionalServices().add(additionalService);
        cartRepository.save(cart);
        return cart;
    }

    public Cart removeAdditionalService(AdditionalServiceDto additionalServiceDto) {
        AdditionalService additionalService = additionalServicesService.getById(additionalServiceDto.getId()).orElseThrow(() ->
                new AdditionalServiceNotFoundEx("Additional Service not found " + additionalServiceDto));
        Cart cart = getCart();
        if (!cart.getAdditionalServices().contains(additionalService)) {
            throw new AdditionalServiceNotFoundEx("Additional service not found in cart! " + additionalServiceDto);
        }
        cart.getAdditionalServices().remove(additionalService);
        cartRepository.save(cart);
        return cart;
    }

}
