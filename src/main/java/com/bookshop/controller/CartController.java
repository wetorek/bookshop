package com.bookshop.controller;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.entity.Cart;
import com.bookshop.mapper.CartMapper;
import com.bookshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCartResponse() {
        Cart cart = cartService.getCart();
        return cartMapper.mapCartToDto(cart);
    }
}
