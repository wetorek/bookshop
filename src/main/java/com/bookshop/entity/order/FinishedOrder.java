package com.bookshop.entity.order;

import com.bookshop.exceptions.OrderChangeNotAllowedEx;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@NoArgsConstructor
@Slf4j
public class FinishedOrder extends OrderState {

    @Transient
    private boolean finished = false;

    public FinishedOrder(OrderStatus orderStatus) {
        super(orderStatus);
        finishOrder();
    }

    @Override
    public void finishOrder() {
        if (finished) {
            throw new OrderChangeNotAllowedEx("This operation is not allowed");
        }
        log.info("Finished order!");
        finished = true;
    }
}
