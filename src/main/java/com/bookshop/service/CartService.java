package com.bookshop.service;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.entity.Cart;
import com.bookshop.entity.User;
import com.bookshop.mapper.CartMapper;
import com.bookshop.repository.CartRepository;
import lombok.AllArgsConstructor;

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

    public CartDto getCartDto() {
        return cartMapper.mapCartToDto(getCart());
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setAdditionalServices(new LinkedList<>()); //TODO is required?
        cart.setCartItems(new LinkedList<>());
        cart.setTotal(BigDecimal.ZERO);
        cart.setUser(user);
        return cart;
    }
}
