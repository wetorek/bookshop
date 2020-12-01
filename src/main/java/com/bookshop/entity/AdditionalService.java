package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ADDITIONAL_SERVICES")
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private BigDecimal price;
    private String description;
}
