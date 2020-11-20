package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.controller.dto.CartDto;
import com.bookshop.controller.dto.CartItemRequest;
import com.bookshop.entity.*;
import com.bookshop.exceptions.BookNotFoundException;
import com.bookshop.mapper.CartMapper;
import com.bookshop.repository.CartRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final CartMapper cartMapper;
    private final BookService bookService;
    private final AdditionalServicesService additionalServicesService;

    public Cart getCart() {
        User user = authService.getCurrentUser();
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);
        return cartOptional.orElseGet(() -> {
            Cart tempCart = createNewCart(user);
            cartRepository.save(tempCart);
            return tempCart;
        });
    }

    public Cart addItemToCart(CartItemRequest cartItemRequest) {
        if (bookService.getBookByID(cartItemRequest.getBooksId()).isEmpty()) {
            throw new BookNotFoundException(cartItemRequest.toString());
        }
        CartItem cartItem = buildCartItem(bookService, cartItemRequest);
        if (cartItem.getAmountOfItems() > cartItem.getBook().getInSock()) {
            //return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        addCartItemToCart(getCart(), cartItem);
        return getCart();
    }

    private void addCartItemToCart(Cart cart, CartItem cartItem) { //strategy
        if (!doesCartContainProduct(cart, cartItem)) {
            cart.getCartItems().add(cartItem);
            cart.setTotal(cart.getTotal().add(cart.getTotal()));
        } else {
            CartItem cartItemFromCart = getCartItemFromCartByBookId(cart, cartItem.getBook().getId()).orElseThrow(() -> new RuntimeException("no item found, error"));
            cartItemFromCart.setAmountOfItems(cartItemFromCart.getAmountOfItems() + cartItem.getAmountOfItems());
            cartItemFromCart.setSubTotal(cartItemFromCart.getSubTotal().add(cartItem.getSubTotal()));
            cart.setTotal(cart.getTotal().add(cartItem.getSubTotal()));
        }
    }

    private Optional<CartItem> getCartItemFromCartByBookId(Cart cart, Long id) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(id))
                .findFirst();
    }

    private CartItem buildCartItem(BookService bookService, CartItemRequest cartItemRequest) {
        Book book = bookService.getBookByID(cartItemRequest.getBooksId()).orElseThrow(() -> new RuntimeException("Book not found"));
        BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getAmountOfItems()));
        return CartItem.builder()
                .amountOfItems(cartItemRequest.getAmountOfItems())
                .book(book)
                .subTotal(price)
                .build();
    }


    public ResponseEntity<CartDto> removeItemFromCart(CartItemRequest cartItemRequest) {
        Cart cart = getCart();
        if (!doesCartContainProduct(cart, cartItemRequest)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CartItem cartItem = getCartItemFromCartByBookId(cart, cartItemRequest.getBooksId()).orElseThrow(() -> new RuntimeException("Unexpected error occured"));
        if (!cartItem.getAmountOfItems().equals(cartItemRequest.getAmountOfItems())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cart.getCartItems().remove(cartItem);
        cart.setTotal(cart.getTotal().subtract(cartItem.getSubTotal()));
        return new ResponseEntity<>(cartMapper.mapCartToDto(cart), HttpStatus.OK);
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

    private Cart createNewCart(User user) {
        return Cart.builder()
                .additionalServices(new LinkedList<>())
                .cartItems(new LinkedList<>())
                .total(BigDecimal.ZERO)
                .user(user)
                .build();
    }

    public ResponseEntity<CartDto> addAdditionalService(AdditionalServiceDto additionalServiceDto) {
        Optional<AdditionalService> additionalService = additionalServicesService.getById(additionalServiceDto.getId());
        if (additionalService.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Cart cart = getCart();
        additionalService.ifPresent(cart.getAdditionalServices()::add);
        return new ResponseEntity<>(cartMapper.mapCartToDto(cart), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> removeAdditionalService(AdditionalServiceDto additionalServiceDto) {
        Cart cart = getCart();
        Optional<AdditionalService> additionalService = additionalServicesService.getById(additionalServiceDto.getId());
        if (additionalService.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        additionalService.ifPresent(cart.getAdditionalServices()::remove);
        return new ResponseEntity<>(cartMapper.mapCartToDto(cart), HttpStatus.OK);

    }


}
