package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@NoArgsConstructor
@Slf4j
public class PaidOrder extends OrderState {

    @Transient
    private boolean paid = false;

    public PaidOrder(OrderStatus orderStatus) {
        super(orderStatus);
        pay();
    }


    @Override
    public void pay() {
        if (paid) {
            throw new OrderChangeNotAllowedEx("This operation is not allowed");
        }
        log.info("Paying for order!");
        paid = true;
    }

    @Override
    public void finishOrder() {
        getOrderStatus().setOrderState(new FinishedOrder(getOrderStatus()));
    }
}
