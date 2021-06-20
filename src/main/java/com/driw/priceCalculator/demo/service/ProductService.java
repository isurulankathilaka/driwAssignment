package com.driw.priceCalculator.demo.service;

import com.driw.priceCalculator.demo.dto.ProductPriceDto;
import com.driw.priceCalculator.demo.model.*;
import com.driw.priceCalculator.demo.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.driw.priceCalculator.demo.Constants.CARTONS;
import static com.driw.priceCalculator.demo.Constants.SINGLE_UNITS;

/**
 * This class is responsible for calculate and return prices
 */
@Component
public class ProductService {

    public static final int CARTON_DISCOUNT_THRESHOLD = 3;
    public static final double CARTON_DISCOUNT_PERCENTAGE = 0.9;
    @Autowired
    public ProductDao productDao;

    HashMap<Integer, Product> productHashMap = new HashMap<>();

    /**
     * @param id
     * @param quantity
     * @return get product using product Id
     * calculate price
     * return price
     */
    public List<ProductPriceDto> findPriceList(int id, int quantity) {
        return getProductPriceList(getProductByProductId(id), id, quantity);
    }

    /**
     * @param id
     * @param unitType
     * @param quantity
     * @return
     */
    public ProductPriceDto priceCalculator(int id, String unitType, int quantity) {
        ProductPriceDto productPriceDto = new ProductPriceDto();
        productPriceDto.setQuantity(quantity);
        productPriceDto.setPrice(getTotalPrice(getProductByProductId(id), quantity, true, unitType));
        return productPriceDto;
    }

    /**
     * @param product
     * @param id
     * @param quantity
     * @return
     */
    private List<ProductPriceDto> getProductPriceList(Product product, long id, int quantity) {
        List<ProductPriceDto> productPriceDtoListArray = new ArrayList<>();

        for (int productCount = 1; productCount <= quantity; productCount++) {
            ProductPriceDto productPriceDto = new ProductPriceDto();
            productPriceDto.setQuantity(productCount);
            productPriceDto.setPrice(getTotalPrice(product, productCount, false, null));
            productPriceDtoListArray.add(productPriceDto);
        }

        return productPriceDtoListArray;
    }

    /**
     * @param product
     * @param quantity
     * @return This method will return total price
     * totalPrice = carton price + single product price
     */
    private double getTotalPrice(Product product, int quantity, boolean isPriceCalculateUsingSingleType, String unitType) {
        double totalPrice = 0;

        if (isPriceCalculateUsingSingleType && unitType != null) {
            if (unitType.equalsIgnoreCase(CARTONS)) {
                totalPrice = getCartonPrice(product.getQtyPerCarton(), product.getCartonPrice(), quantity, isPriceCalculateUsingSingleType);
            } else if (unitType.equalsIgnoreCase(SINGLE_UNITS)) {
                totalPrice = getSingleUnitPrice(product.getQtyPerCarton(), product.getCartonPrice(), quantity, isPriceCalculateUsingSingleType);
            }
        } else if (!isPriceCalculateUsingSingleType) {
            totalPrice = getCartonPrice(product.getQtyPerCarton(), product.getCartonPrice(), quantity, isPriceCalculateUsingSingleType) +
                    getSingleUnitPrice(product.getQtyPerCarton(), product.getCartonPrice(), quantity, isPriceCalculateUsingSingleType);
        }

        return totalPrice;
    }

    /**
     * @param qtyPerCarton
     * @param cartonPrice
     * @param quantity
     * @return This method is responsible for calculate single unit price.
     * when purchasing single units needs to pay 30% more
     */
    private double getSingleUnitPrice(int qtyPerCarton, double cartonPrice, int quantity, boolean isPriceCalculateUsingSingleType) {
        int numberOfSingleUnits = quantity;

        if (!isPriceCalculateUsingSingleType) {
            numberOfSingleUnits = getNumberOfunits(qtyPerCarton, quantity, SINGLE_UNITS);
        }

        return (cartonPrice / qtyPerCarton) * numberOfSingleUnits * 1.3;
    }

    /**
     * @param qtyPerCarton
     * @param cartonPrice
     * @param quantity
     * @return This method is responsible for calculate carton price.
     * if number of cartons >= 3 , will receive a 10% discount
     */
    private double getCartonPrice(int qtyPerCarton, double cartonPrice, int quantity, boolean isPriceCalculateUsingSingleType) {
        double totalCartonPrice = 0;
        int numberOfCartons;

        if (qtyPerCarton > 0 && quantity > 0) {
            numberOfCartons = !isPriceCalculateUsingSingleType ? getNumberOfunits(qtyPerCarton, quantity, CARTONS) : quantity;

            if (numberOfCartons >= CARTON_DISCOUNT_THRESHOLD) {
                totalCartonPrice = cartonPrice * numberOfCartons * CARTON_DISCOUNT_PERCENTAGE;
            } else {
                totalCartonPrice = numberOfCartons * cartonPrice;
            }
        }
        return totalCartonPrice;
    }

    /**
     * @param qtyPerCarton
     * @param quantity
     * @param unitType
     * @return This method is responsible for find number of units according to unit type(Cartons or single units )
     */
    private int getNumberOfunits(int qtyPerCarton, int quantity, String unitType) {
        if (unitType.equalsIgnoreCase(CARTONS)) {
            return quantity / qtyPerCarton;
        } else if (unitType.equalsIgnoreCase(SINGLE_UNITS)) {
            return quantity % qtyPerCarton;
        }
        return 0;
    }

    /**
     * @param productId
     * @return
     */
    private Product getProductByProductId(int productId) {

        Product product;

        if (productHashMap.containsKey(productId)) {
            product = productHashMap.get(productId);
        } else {
            product = getProductFromDB(productId);
            if (product != null) {
                productHashMap.put(productId, product);
            }

        }
        return product;
    }

    /**
     * @param productId
     * @return This method is responsible for return Product object for givern product Id
     */
    private Product getProductFromDB(int productId) {
        return productDao.findByProductId(productId);
    }
}
