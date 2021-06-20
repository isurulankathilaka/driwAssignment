package com.driw.priceCalculator.demo.service;

import com.driw.priceCalculator.demo.dto.ProductPriceDto;
import com.driw.priceCalculator.demo.model.Product;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.driw.priceCalculator.demo.Constants.CARTONS;
import static com.driw.priceCalculator.demo.Constants.SINGLE_UNITS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    ProductService productServiceMock;

    @Before
    public void initMethod() {
        productServiceMock = new ProductService();
        productServiceMock.productHashMap = (HashMap<Integer, Product>) mockedProductHashMap();
    }

    @Test
    public void price_engine_findPriceList_test_Penguin_ears() {
        int productId = 1;
        int quantity = 50;


        List<ProductPriceDto> productPriceDtos = productServiceMock.findPriceList(productId, quantity);
        assertEquals(quantity, productPriceDtos.size());

        assertEquals(1, productPriceDtos.get(0).getQuantity());
        assertEquals(11.375, productPriceDtos.get(0).getPrice());

        assertEquals(3, productPriceDtos.get(2).getQuantity());
        assertEquals(45.5, productPriceDtos.get(3).getPrice());

        assertEquals(10, productPriceDtos.get(9).getQuantity());
        assertEquals(113.75, productPriceDtos.get(9).getPrice());

        assertEquals(50, productPriceDtos.get(49).getQuantity());
        assertEquals(463.75, productPriceDtos.get(49).getPrice());
    }

    @Test
    public void price_engine_findPriceList_test_Horseshoe() {
        int productId = 2;
        int quantity = 50;

        List<ProductPriceDto> productPriceDtos = productServiceMock.findPriceList(productId, quantity);
        assertEquals(quantity, productPriceDtos.size());

        assertEquals(1, productPriceDtos.get(0).getQuantity());
        assertEquals(214.5, productPriceDtos.get(0).getPrice());

        assertEquals(3, productPriceDtos.get(2).getQuantity());
        assertEquals(858.0, productPriceDtos.get(3).getPrice());

        assertEquals(10, productPriceDtos.get(9).getQuantity());
        assertEquals(1650.0, productPriceDtos.get(9).getPrice());

        assertEquals(50, productPriceDtos.get(49).getQuantity());
        assertEquals(7425.0, productPriceDtos.get(49).getPrice());
    }

    @Test
    public void price_calculator_findPriceList_penguin_ears_cartons_test() {
        int productId = 1;

        List<Integer> quantityList = new ArrayList<>();
        quantityList.add(1);
        quantityList.add(3);
        quantityList.add(4);
        quantityList.add(10);
        quantityList.add(50);

        for( Integer quantity : quantityList)
        {
            ProductPriceDto productPriceDto = productServiceMock.priceCalculator(productId,CARTONS ,quantity);

            assertEquals(quantity, productPriceDto.getQuantity());
            assertEquals( cartons_penguin_ears_price_HashMap().get(quantity) , productPriceDto.getPrice());
        }
    }

    @Test
    public void price_calculator_findPriceList_penguin_ears_single_units_test() {
        int productId = 1;

        List<Integer> quantityList = new ArrayList<>();
        quantityList.add(1);
        quantityList.add(3);
        quantityList.add(4);
        quantityList.add(10);
        quantityList.add(50);

        for( Integer quantity : quantityList)
        {
            ProductPriceDto productPriceDto = productServiceMock.priceCalculator(productId,SINGLE_UNITS ,quantity);

            assertEquals(quantity, productPriceDto.getQuantity());
            assertEquals( single_unit_penguin_ears_price_HashMap().get(quantity) , productPriceDto.getPrice());
        }
    }

    @Test
    public void price_calculator_findPriceList_horseshoe_cartons_test() {
        int productId = 2;

        List<Integer> quantityList = new ArrayList<>();
        quantityList.add(1);
        quantityList.add(3);
        quantityList.add(4);
        quantityList.add(10);
        quantityList.add(50);

        for( Integer quantity : quantityList)
        {
            ProductPriceDto productPriceDto = productServiceMock.priceCalculator(productId,CARTONS ,quantity);

            assertEquals(quantity, productPriceDto.getQuantity());
            assertEquals( cartons_horseshoe_price_HashMap().get(quantity) , productPriceDto.getPrice());
        }
    }

    @Test
    public void price_calculator_findPriceList_horseshoe_single_units_test() {
        int productId = 2;

        List<Integer> quantityList = new ArrayList<>();
        quantityList.add(1);
        quantityList.add(3);
        quantityList.add(4);
        quantityList.add(10);
        quantityList.add(50);

        for( Integer quantity : quantityList)
        {
            ProductPriceDto productPriceDto = productServiceMock.priceCalculator(productId,SINGLE_UNITS ,quantity);

            assertEquals(quantity, productPriceDto.getQuantity());
            assertEquals( single_unit_horseshoe_price_HashMap().get(quantity) , productPriceDto.getPrice());
        }
    }

    private Map<Integer, Double> cartons_horseshoe_price_HashMap()
    {
        Map<Integer, Double> productMap = new HashMap<>();

        productMap.put(1, 825.0);
        productMap.put(3, 2227.5);
        productMap.put(4,2970.0 );
        productMap.put(10,7425.0);
        productMap.put(50,37125.0);

        return productMap;
    }


    private Map<Integer, Double> single_unit_horseshoe_price_HashMap()
    {
        Map<Integer, Double> productMap = new HashMap<>();

        productMap.put(1, 214.5);
        productMap.put(3, 643.5);
        productMap.put(4,858.0 );
        productMap.put(10,2145.0);
        productMap.put(50,10725.0);

        return productMap;
    }

    private Map<Integer, Double> single_unit_penguin_ears_price_HashMap()
    {
        Map<Integer, Double> productMap = new HashMap<>();

        productMap.put(1, 11.375);
        productMap.put(3, 34.125);
        productMap.put(4, 45.5 );
        productMap.put(10,113.75);
        productMap.put(50,568.75);

        return productMap;
    }

    private Map<Integer, Double> cartons_penguin_ears_price_HashMap()
    {
        Map<Integer, Double> productMap = new HashMap<>();

        productMap.put(1, 175.0);
        productMap.put(3, 472.5);
        productMap.put(4, 630.0 );
        productMap.put(10,1575.0);
        productMap.put(50,7875.0);

        return productMap;
    }

    private Map<Integer, Product> mockedProductHashMap()
    {
        Map<Integer, Product> productMap = new HashMap<>();

        Product product_penguin = new Product();
        product_penguin.setProductId(1);
        product_penguin.setName( "Penguin-ears" );
        product_penguin.setQtyPerCarton(20);
        product_penguin.setCartonPrice(175.0);

        Product product_Horseshoe= new Product();
        product_Horseshoe.setProductId(2);
        product_Horseshoe.setName( "Horseshoe" );
        product_Horseshoe.setQtyPerCarton(5);
        product_Horseshoe.setCartonPrice(825);

        productMap.put(1, product_penguin);
        productMap.put(2, product_Horseshoe);

        return productMap;

    }

}