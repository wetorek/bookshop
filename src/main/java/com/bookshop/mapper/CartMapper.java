package com.bookshop.mapper;

import com.bookshop.controller.dto.CartDto;
import com.bookshop.controller.dto.CartItemDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Cart;
import com.bookshop.entity.CartItem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CartMapper {
    private final ModelMapper modelMapper;
    private final AdditionalServicesMapper additionalServicesMapper;
    private final BookMapper bookMapper;

    public CartDto mapCartToDto(Cart cart) {
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cartDto.setAdditionalServices(additionalServicesMapper.mapListOfEntitiesToDto(cart.getAdditionalServices()));
        List<CartItemDto> cartItemDtoList = cart.getCartItems()
                .stream()
                .map(this::mapCartItemToDto)
                .collect(Collectors.toList());
        cartDto.setCartItems(cartItemDtoList);
        return cartDto;
    }

    public CartItemDto mapCartItemToDto(CartItem cartItem) {
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        List<BookDto> bookDtoList = bookMapper.mapListOfEntitiesToDto(cartItem.getBooks());
        cartItemDto.setBooks(bookDtoList);
        return cartItemDto;
    }
}
