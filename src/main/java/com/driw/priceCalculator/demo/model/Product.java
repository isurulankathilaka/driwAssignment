package com.driw.priceCalculator.demo.model;

public class Product {

    private int product_Id;

    private String name;

    private double cartonPrice;

    private int qtyPerCarton;

    public int getProductId() {
        return product_Id;
    }

    public void setProductId(int productId) {
        this.product_Id = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(double cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public int getQtyPerCarton() {
        return qtyPerCarton;
    }

    public void setQtyPerCarton(int qtyPerCarton) {
        this.qtyPerCarton = qtyPerCarton;
    }
}
