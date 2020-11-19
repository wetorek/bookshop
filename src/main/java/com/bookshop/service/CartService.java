package com.bookshop.service;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.entity.Cart;
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

    public Cart getCart() {
        User user = authService.getCurrentUser();
        Optional<Cart> cartOptional = cartRepository.findCartByUser(user);
        return cartOptional.orElseGet(() -> {
            Cart tempCart = createNewCart(user);
            cartRepository.save(tempCart);
            return tempCart;
        });
    }

    public ResponseEntity<CartDto> getCartDto() {
        return new ResponseEntity<>(cartMapper.mapCartToDto(getCart()), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> addItemToCart() {
        //TODO create BookRequest and BookResponse
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
