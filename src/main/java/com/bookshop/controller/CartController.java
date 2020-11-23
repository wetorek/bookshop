package com.bookshop.controller;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.controller.dto.CartDto;
import com.bookshop.controller.dto.CartItemRequest;
import com.bookshop.entity.Cart;
import com.bookshop.mapper.CartMapper;
import com.bookshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCartResponse() {
        Cart cart = cartService.getCart();
        return cartMapper.mapCartToDto(cart);
    }

    @PatchMapping("/cart-item/add")
    @ResponseStatus(HttpStatus.OK)
    public CartDto addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        Cart cart = cartService.addItemToCart(cartItemRequest);
        return cartMapper.mapCartToDto(cart);
    }

    @PatchMapping("/cart-item/remove")
    @ResponseStatus(HttpStatus.OK)
    public CartDto removeItemFromCart(@RequestBody CartItemRequest cartItemRequest) {
        Cart cart = cartService.removeItemFromCart(cartItemRequest);
        return cartMapper.mapCartToDto(cart);
    }

    @PatchMapping("additional-service/add")
    @ResponseStatus(HttpStatus.OK)
    public CartDto addAdditionalServ(@RequestBody AdditionalServiceDto additionalServiceDto) {
        Cart cart = cartService.addAdditionalService(additionalServiceDto);
        return cartMapper.mapCartToDto(cart);
    }

    @PatchMapping("additional-service/remove")
    @ResponseStatus(HttpStatus.OK)
    public CartDto removeAdditionalServ(@RequestBody AdditionalServiceDto additionalServiceDto) {
        Cart cart = cartService.removeAdditionalService(additionalServiceDto);
        return cartMapper.mapCartToDto(cart);
    }

}
