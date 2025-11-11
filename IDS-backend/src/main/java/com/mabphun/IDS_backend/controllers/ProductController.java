package com.mabphun.IDS_backend.controllers;

import com.mabphun.IDS_backend.dtos.ProductFilterDto;
import com.mabphun.IDS_backend.entities.Product;
import com.mabphun.IDS_backend.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        List<String> errors = productService.handleUploadedFile(file);

        if (errors.isEmpty()){
            return ResponseEntity.ok(errors);
        }
        else {
            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PostMapping("/products")
    public Page<Product> getProducts(@RequestBody ProductFilterDto filterDto) {
        return productService.getProducts(filterDto);
    }

    @PostMapping("/faulty-products")
    public Page<Product> getFaultyProducts(@RequestBody ProductFilterDto filterDto) {
        return productService.getFaultyProducts(filterDto);
    }

}
