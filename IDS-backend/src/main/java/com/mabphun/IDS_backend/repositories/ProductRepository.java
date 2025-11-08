package com.mabphun.IDS_backend.repositories;

import com.mabphun.IDS_backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
