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
        return new AuthorMapper(new ModelMapper());
    }

    @Bean
    public BookMapper bookMapper() {
        return new BookMapper(new ModelMapper());
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper(new ModelMapper());
    }

    @Bean
    public PublisherMapper publisherMapper() {
        return new PublisherMapper(new ModelMapper());
    }

    @Bean
    public AdditionalServicesMapper additionalServicesMapper() {
        return new AdditionalServicesMapper(new ModelMapper());
    }


    @Bean
    public UserMapper userMapper() {
        return new UserMapper(new ModelMapper());
    }
}
