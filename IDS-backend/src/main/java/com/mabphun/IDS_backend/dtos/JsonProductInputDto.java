package com.mabphun.IDS_backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class JsonProductInputDto {

    private String id;

    private String name;

    private String manufacturer;

    private BigDecimal netPrice;

    private String currency;

    private BigDecimal vatRate;

    private Long quantityAvailable;

    private Long ean;

    private LocalDateTime updatedAt;
}
