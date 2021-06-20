package com.driw.priceCalculator.demo.dao.mapper;

import com.driw.priceCalculator.demo.Constants;
import com.driw.priceCalculator.demo.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt(Constants.COLUMN_PRODUCT_ID));
        product.setName(rs.getString(Constants.COLUMN_NAME));
        product.setCartonPrice(rs.getDouble(Constants.COLUMN_CARTON_PRICE));
        product.setQtyPerCarton(rs.getInt(Constants.COLUMN_QTY_PER_CARTON));
        return product;
    }
}