package com.mabphun.IDS_backend.repositories;

import com.mabphun.IDS_backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("""
        SELECT p FROM Product p
        WHERE
            (:sku IS NULL OR LOWER(p.sku) LIKE :sku)
        AND (:name IS NULL OR LOWER(p.name) LIKE :name)
        AND (:manufacturer IS NULL OR LOWER(p.manufacturer) LIKE :manufacturer)
        AND (:priceMin IS NULL OR p.finalPriceHuf >= :priceMin)
        AND (:priceMax IS NULL OR p.finalPriceHuf <= :priceMax)
        AND (:stockMin IS NULL OR p.stock >= :stockMin)
        AND (:stockMax IS NULL OR p.stock <= :stockMax)
        AND (:ean IS NULL OR p.ean = :ean)
        AND (CAST(:updatedAtMin AS timestamp) IS NULL OR p.updatedAt IS NULL OR p.updatedAt >= :updatedAtMin)
        AND (CAST(:updatedAtMax AS timestamp) IS NULL OR p.updatedAt IS NULL OR p.updatedAt <= :updatedAtMax)
        AND (:source IS NULL OR LOWER(p.source) LIKE :source)
        AND (:onlyValid IS NULL OR :onlyValid = FALSE OR (:onlyValid = TRUE
        AND p.name IS NOT NULL
        AND p.manufacturer IS NOT NULL
        AND p.finalPriceHuf IS NOT NULL
        AND p.stock IS NOT NULL
        AND p.ean IS NOT NULL
        AND p.updatedAt IS NOT NULL))
    """)
    Page<Product> findFiltered(
            @Param("sku") String sku,
            @Param("name") String name,
            @Param("manufacturer") String manufacturer,
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("stockMin") Long stockMin,
            @Param("stockMax") Long stockMax,
            @Param("ean") Long ean,
            @Param("updatedAtMin") LocalDateTime updatedAtMin,
            @Param("updatedAtMax") LocalDateTime updatedAtMax,
            @Param("source") String source,
            @Param("onlyValid") Boolean onlyValid,
            Pageable pageable
    );

}
