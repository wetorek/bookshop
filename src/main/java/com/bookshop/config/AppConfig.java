package com.bookshop.config;

import com.bookshop.mapper.OrderMapper;
import com.bookshop.mapper.UserMapper;
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
    public UserMapper userMapper() {
        return new UserMapper(modelMapper());
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper(modelMapper());
    }
}
