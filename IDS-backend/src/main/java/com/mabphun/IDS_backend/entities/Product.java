package com.mabphun.IDS_backend.entities;

import com.mabphun.IDS_backend.dtos.CsvProductInputDto;
import com.mabphun.IDS_backend.dtos.JsonProductInputDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    @Column
    private String name;

    @Column
    private String manufacturer;

    @Column(precision = 38, scale = 2)
    private BigDecimal finalPriceHuf;

    @Column
    private Long stock;

    @Column
    private Long ean;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String source;

    public static Product fromJsonProductInputDto(JsonProductInputDto dto){
        return Product.builder()
                .sku(dto.getId())
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .finalPriceHuf(dto.getNetPrice() != null && dto.getVatRate() != null ? dto.getNetPrice().multiply(dto.getVatRate().add(BigDecimal.ONE)) : null)
                .stock(dto.getQuantityAvailable())
                .ean(dto.getEan())
                .updatedAt(dto.getUpdatedAt())
                .source("json")
                .build();
    }

    public static Product fromCsvProductInputDto(CsvProductInputDto dto){
        return Product.builder()
                .sku(dto.getSku())
                .name(dto.getProductName())
                .manufacturer(dto.getBrand())
                .finalPriceHuf(dto.getGrossPriceHuf())
                .stock(dto.getStockQty())
                .ean(null)
                .updatedAt(null)
                .source("csv")
                .build();
    }
}
