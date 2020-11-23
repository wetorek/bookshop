/*
package com.bookshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "ORDER_STATE")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    abstract Order placeNewOrder();

    abstract void pay();

    abstract void finishOrder();
}
*/
