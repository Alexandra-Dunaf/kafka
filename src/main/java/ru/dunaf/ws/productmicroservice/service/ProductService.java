package ru.dunaf.ws.productmicroservice.service;

import ru.dunaf.ws.productmicroservice.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    String createdProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException;
}
