package com.driw.priceCalculator.demo.dao;

import com.driw.priceCalculator.demo.Constants;
import com.driw.priceCalculator.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ProductDao {
    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Product findByProductId(int productId) {
        Map parametersMap = new HashMap();
        parametersMap.put("id", productId);
        Product product = new Product();

        try {
            product = (Product) namedParameterJdbcTemplate.queryForObject("SELECT * FROM " + Constants.TABLE_PRODUCT +
                    " WHERE " + Constants.COLUMN_PRODUCT_ID + " = :id ", parametersMap, new ProductMapper());
        } catch (DataAccessException ex) {

            ex.printStackTrace();
        }

        return product;
    }

    public List<Product> findAll() {

        List<Product> productList = new ArrayList<>();

        namedParameterJdbcTemplate.query("SELECT * FROM " + Constants.TABLE_PRODUCT, resultSet -> {
            Product product = new Product();
            product.setProductId(resultSet.getInt(Constants.COLUMN_PRODUCT_ID));
            productList.add(product);

        });

        return productList;
    }

    class ProductMapper implements RowMapper {

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
}
