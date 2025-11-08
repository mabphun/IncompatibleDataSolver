package com.mabphun.IDS_backend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CsvProductInputDto {

    private String sku;

    private String productName;

    private String brand;

    private BigDecimal grossPriceHuf;

    private Long stockQty;

    public static String skuField = "sku";
    public static String productNameField = "product_name";
    public static String brandField = "brand";
    public static String grossPriceHufField = "gross_price_huf";
    public static String stockQtyField = "stock_qty";
}
