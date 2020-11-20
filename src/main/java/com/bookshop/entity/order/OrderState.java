package com.bookshop.entity.order;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    abstract Order placeNewOrder();

    abstract void pay();

    abstract void finishOrder();
}
