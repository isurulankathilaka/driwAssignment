package com.driw.priceCalculator.demo.controller;


import com.driw.priceCalculator.demo.dto.ProductPriceDto;
import com.driw.priceCalculator.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @CrossOrigin
    @RequestMapping(path = "priceEngine", method = RequestMethod.GET)
    public ResponseEntity<List<ProductPriceDto>> getProductPriceList(@RequestParam("productId") int productId,
                                                                     @RequestParam("quantity") int quantity) {
        List<ProductPriceDto> productPriceDto = productService.findPriceList(productId, quantity);

        if (!productPriceDto.isEmpty()) {
            return new ResponseEntity<>(productPriceDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @RequestMapping(path = "priceCalculator", method = RequestMethod.GET)
    public ResponseEntity<ProductPriceDto> getProductPriceCalculator(@RequestParam("productId") int productId,
                                                                     @RequestParam("unitType") String unitType,
                                                                     @RequestParam("quantity") int quantity) {
        ProductPriceDto productPriceDto = productService.priceCalculator(productId, unitType, quantity);

        if (productPriceDto != null) {
            return new ResponseEntity<>(productPriceDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
