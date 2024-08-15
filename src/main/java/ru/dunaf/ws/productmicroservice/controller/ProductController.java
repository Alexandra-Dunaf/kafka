package ru.dunaf.ws.productmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dunaf.ws.productmicroservice.service.ProductService;
import ru.dunaf.ws.productmicroservice.service.dto.CreateProductDto;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto createProductDto) {
        String productId = null;
        try {
            productId = productService.createdProduct(createProductDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
