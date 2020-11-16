package com.bookshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "additionalServices")
public class AdditionalService {
    @Id
    private Long Id;
    private BigDecimal price;
    private BigDecimal description;
}
