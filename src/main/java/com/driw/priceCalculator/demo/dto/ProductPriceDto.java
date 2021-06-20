package com.driw.priceCalculator.demo.dto;

import org.springframework.stereotype.Component;

@Component
public class ProductPriceDto {

    int quantity;
    double price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
