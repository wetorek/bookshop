package com.bookshop.service;

import com.bookshop.entity.Cart;
import com.bookshop.entity.User;
import com.bookshop.repository.CartRepository;
import com.bookshop.service.book.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private AuthService authService;
    @Mock
    private BookService bookService;
    @Mock
    private AdditionalServicesService additionalServicesService;
    @Mock
    private CartRepository cartRepository;

    @Test
    void createNewCart() {
        //given
        User user = new User(1L, "user", "password", "mail", LocalDateTime.now(), true);
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        Optional<Cart> optionalCart = Optional.empty();
        Mockito.when(cartRepository.findCartByUsername(user.getUsername())).thenReturn(optionalCart);

        //when
        Cart cart = cartService.getCart();

        //then
        Assertions.assertEquals(cart.getUsername(), user.getUsername());
        Mockito.verify(cartRepository, Mockito.times(1)).save(cart);
    }

    @Test
    void getExistingCart() {
        //given
        User user = new User(1L, "user", "password", "mail", LocalDateTime.now(), true);
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        Cart cartMock = Mockito.mock(Cart.class);
        Mockito.when(cartRepository.findCartByUsername(user.getUsername())).thenReturn(Optional.of(cartMock));

        //when
        Cart cart = cartService.getCart();

        //then
        Assertions.assertEquals(cart, cartMock);
        Mockito.verify(cartRepository, Mockito.only()).findCartByUsername(user.getUsername());
    }
}