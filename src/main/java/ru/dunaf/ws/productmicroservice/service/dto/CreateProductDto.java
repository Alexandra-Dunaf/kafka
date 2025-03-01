package ru.dunaf.ws.productmicroservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductDto {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
