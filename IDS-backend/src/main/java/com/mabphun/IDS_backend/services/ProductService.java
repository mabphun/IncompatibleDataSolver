package com.mabphun.IDS_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mabphun.IDS_backend.dtos.CsvProductInputDto;
import com.mabphun.IDS_backend.dtos.JsonProductInputDto;
import com.mabphun.IDS_backend.dtos.ProductFilterDto;
import com.mabphun.IDS_backend.entities.Product;
import com.mabphun.IDS_backend.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Page<Product> getProducts(ProductFilterDto filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                10,
                Sort.by(Sort.Direction.fromString(filter.getSortDir()), filter.getSortBy())
        );

        return productRepository.findFiltered(
                filter.getSku() != null && !filter.getSku().isEmpty()? filter.getSku().toLowerCase() + "%" : null,
                filter.getName() != null && !filter.getName().isEmpty()? filter.getName().toLowerCase() + "%" : null,
                filter.getManufacturer() != null && !filter.getManufacturer().isEmpty()? filter.getManufacturer().toLowerCase() + "%" : null,
                filter.getPriceMin(),
                filter.getPriceMax(),
                filter.getStockMin(),
                filter.getStockMax(),
                filter.getEan(),
                filter.getUpdatedAtMin() != null ? filter.getUpdatedAtMin().atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0),
                filter.getUpdatedAtMax() != null ? filter.getUpdatedAtMax().plusDays(1).atStartOfDay() : LocalDateTime.of(9999, 12, 31, 23, 59),
                filter.getSource() != null && !filter.getSource().isEmpty()? filter.getSource().toLowerCase() + "%" : null,
                filter.getOnlyValid() != null ? filter.getOnlyValid() : false,
                pageable
        );
    }

    public Page<Product> getFaultyProducts(ProductFilterDto filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                10,
                Sort.by(Sort.Direction.fromString(filter.getSortDir()), filter.getSortBy())
        );

        return productRepository.findFaultyProducts(pageable);
    }

    public List<String> handleUploadedFile(MultipartFile file){
        List<Product> products = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        if (Objects.equals(file.getContentType(), "application/json")){
            Pair<List<Product>, List<String>> responsePair = parseJsonFile(file);
            products = responsePair.getFirst();
            errors = responsePair.getSecond();
        }
        else if (Objects.equals(file.getContentType(), "text/csv")){
            Pair<List<Product>, List<String>> responsePair = parseCsvFile(file);
            products = responsePair.getFirst();
            errors = responsePair.getSecond();
        }
        else{
            errors.add("Unexpected file type. Please try JSON or CSV.");
        }

        if (!products.isEmpty()){
            productRepository.saveAll(products);
        }

        return errors;
    }

    private Pair<List<Product>, List<String>> parseCsvFile(MultipartFile file){
        List<Product> products = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<CsvProductInputDto> productDtos = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = CSVFormat
                     .Builder.create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : csvParser) {

                String[] row = record.values();

                String sku = row[csvParser.getHeaderMap().get(CsvProductInputDto.skuField)];
                String productName = row[csvParser.getHeaderMap().get(CsvProductInputDto.productNameField)];
                String brand = row[csvParser.getHeaderMap().get(CsvProductInputDto.brandField)];
                String grossPriceHuf = row[csvParser.getHeaderMap().get(CsvProductInputDto.grossPriceHufField)].replace(" ", "");
                String stockQty = row[csvParser.getHeaderMap().get(CsvProductInputDto.stockQtyField)].replace(" ", "");

                CsvProductInputDto dto = CsvProductInputDto.builder()
                                .sku(sku != null && !sku.isEmpty() ? sku : null)
                                .productName(productName != null && !productName.isEmpty() ? productName : null)
                                .brand(brand != null && !brand.isEmpty() ? brand : null)
                                .grossPriceHuf(!grossPriceHuf.isEmpty() ? new BigDecimal(grossPriceHuf) : null)
                                .stockQty(!stockQty.isEmpty() ? Long.parseLong(stockQty) : null)
                                .build();

                productDtos.add(dto);
            }

            for (int i = 0; i < productDtos.size(); i++) {
                CsvProductInputDto dto = productDtos.get(i);
                if (dto.getSku() == null || dto.getSku().isEmpty()) {
                    errors.add("Item " + (i + 1) + " has a faulty identification, saving was canceled.");
                }
                else if (productRepository.findById(dto.getSku()).isPresent() || products.stream().anyMatch(p -> Objects.equals(p.getSku(), dto.getSku()))){
                    errors.add("Item " + (i + 1) + " is already saved to the database, saving was canceled.");
                }
                else {
                    Product product = Product.fromCsvProductInputDto(dto);
                    products.add(product);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return Pair.of(products, errors);
    }

    private Pair<List<Product>, List<String>> parseJsonFile(MultipartFile file){
        List<Product> products = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        try {
            List<JsonProductInputDto> productDtos = objectMapper.readValue(
                    file.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, JsonProductInputDto.class)
            );
            log.debug("{}", productDtos);

            for (int i = 0; i < productDtos.size(); i++) {
                JsonProductInputDto dto = productDtos.get(i);
                if (dto.getId() == null || dto.getId().isEmpty()) {
                    errors.add("Item " + (i + 1) + " has a faulty identification, saving was canceled.");
                }
                else if (productRepository.findById(dto.getId()).isPresent() || products.stream().anyMatch(p -> Objects.equals(p.getSku(), dto.getId()))){
                    errors.add("Item " + (i + 1) + " is already saved to the database, saving was canceled.");
                }
                else {
                    Product product = Product.fromJsonProductInputDto(dto);
                    products.add(product);
                }
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

        return Pair.of(products, errors);
    }
}
