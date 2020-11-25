package com.bookshop.config;

import com.bookshop.mapper.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuthorMapper authorMapper() {
        return new AuthorMapper(modelMapper());
    }

    @Bean
    public BookMapper bookMapper() {
        return new BookMapper(modelMapper());
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper(modelMapper());
    }

    @Bean
    public PublisherMapper publisherMapper() {
        return new PublisherMapper(modelMapper());
    }

    @Bean
    public AdditionalServicesMapper additionalServicesMapper() {
        return new AdditionalServicesMapper(modelMapper());
    }

    @Bean
    public CartMapper cartMapper() {
        return new CartMapper(modelMapper(), additionalServicesMapper(), bookMapper());
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper(modelMapper());
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper(modelMapper());
    }
}
