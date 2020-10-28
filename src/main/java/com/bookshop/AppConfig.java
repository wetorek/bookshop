package com.bookshop;

import com.bookshop.mapper.AuthorMapper;
import com.bookshop.mapper.BookMapper;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.mapper.PublisherMapper;
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
}
