package com.mabphun.IDS_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String manufacturer;

    @Column(nullable = true, precision = 38, scale = 2)
    private BigDecimal finalPriceHuf;

    @Column(nullable = true)
    private Long stock;

    @Column(nullable = true)
    private Long ean;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String source;
}
