package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;


public interface ProductService {
    
    List<Product> getAllProducts();

    // NEW: Sort support
    List<Product> getAllProducts(Sort sort);
    
    Optional<Product> getProductById(Long id);
    
    Product saveProduct(Product product);
    
    void deleteProduct(Long id);
    
    List<Product> searchProducts(String keyword);
    

    List<Product> getProductsByCategory(String category);

    //Tìm kiếm nâng cao theo nhiều tiêu chí
    List<Product> searchProductsAdvanced(
            String name,
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    List<String> getAllCategories();

     // NEW: Pagination search
    Page<Product> searchProducts(String keyword, Pageable pageable);


}
