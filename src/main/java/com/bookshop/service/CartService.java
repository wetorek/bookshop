package com.bookshop.service;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.controller.dto.CartItemRequest;
import com.bookshop.entity.Book;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import com.bookshop.entity.User;
import com.bookshop.mapper.CartMapper;
import com.bookshop.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Optional;

@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final CartMapper cartMapper;
    private final BookService bookService;

    public Cart getCart() {
        User user = authService.getCurrentUser();
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);
        return cartOptional.orElseGet(() -> {
            Cart tempCart = createNewCart(user);
            cartRepository.save(tempCart);
            return tempCart;
        });
    }

    public ResponseEntity<CartDto> getCartResponse() {
        return new ResponseEntity<>(cartMapper.mapCartToDto(getCart()), HttpStatus.OK);
    }

    private CartDto getCartDto() {
        return cartMapper.mapCartToDto(getCart());
    }

    public ResponseEntity<CartDto> addItemToCart(CartItemRequest cartItemRequest) {
        if (bookService.getBookByID(cartItemRequest.getBooksId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CartItem cartItem = mapCartItemRequestToEntity(bookService, cartItemRequest);
        if (cartItem.getAmountOfItems() > cartItem.getBook().getInSock()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        addCartItemToCart(getCart(), cartItem);
        return new ResponseEntity<>(getCartDto(), HttpStatus.OK);
    }

    private void addCartItemToCart(Cart cart, CartItem cartItem) {
        cart.getCartItems().add(cartItem);
        cart.setTotal(cart.getTotal().add(cart.getTotal()));
    }

    private CartItem mapCartItemRequestToEntity(BookService bookService, CartItemRequest cartItemRequest) {
        Book book = bookService.getBookByID(cartItemRequest.getBooksId()).orElseThrow(() -> new RuntimeException("Book not found"));
        BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getAmountOfItems()));
        return CartItem.builder()
                .amountOfItems(cartItemRequest.getAmountOfItems())
                .book(book)
                .subTotal(price)
                .build();
    }

    public ResponseEntity<CartDto> removeItemFromCart() {

    }

    private Cart createNewCart(User user) {
        return Cart.builder()
                .additionalServices(new LinkedList<>())
                .cartItems(new LinkedList<>())
                .total(BigDecimal.ZERO)
                .user(user)
                .build();
    }


}
