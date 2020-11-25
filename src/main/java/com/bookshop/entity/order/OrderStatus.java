package com.bookshop.entity.order;

import com.bookshop.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ORDER_STATUS")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderStatusId;
    @Cascade(CascadeType.ALL)
    @OneToOne
    private Order order;
    @OneToOne(targetEntity = OrderState.class)
    @Cascade(CascadeType.ALL)
    private OrderState orderState;

    public OrderStatus(Cart cart) {
        this.orderState = new NewOrder(this, cart);
    }

    public Optional<Order> createNewOrder(Cart cart) {
        order = orderState.placeNewOrder(cart);
        return Optional.of(order);
    }

    public void finishOrder() {
        orderState.finishOrder();
    }

    public void payForOrder() {
        orderState.pay();
    }
}
