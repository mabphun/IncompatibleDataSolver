package com.mabphun.IDS_backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProductFilterDto {

    private String sku;
    private String name;
    private String manufacturer;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Long stockMin;
    private Long stockMax;
    private Long ean;
    private LocalDate updatedAtMin;
    private LocalDate updatedAtMax;
    private String source;
    private Boolean onlyValid = false;

    private String sortBy = "sku";
    private String sortDir = "asc";
    private Integer page = 0;
}
