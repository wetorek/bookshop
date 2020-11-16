package com.bookshop.mapper;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.entity.Cart;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class CartMapper {
    private final ModelMapper modelMapper;

    public CartDto mapCartToDto (Cart cart){
        CartDto cartDto = modelMapper.map(cart, CartDto.class);

    }
}
