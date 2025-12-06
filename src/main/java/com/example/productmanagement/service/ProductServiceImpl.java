package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    @Override
    public Product saveProduct(Product product) {
        // Validation logic can go here
        return productRepository.save(product);
    }
    
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }
    
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    //  Implement tìm kiếm nâng cao
    @Override
    public List<Product> searchProductsAdvanced(String name, String category, BigDecimal minPrice, BigDecimal maxPrice) {

        // Chuyển chuỗi rỗng thành null để query không bị lỗi
        if (name != null && name.isBlank()) name = null;
        if (category != null && category.isBlank()) category = null;

        return productRepository.searchProducts(name, category, minPrice, maxPrice);
    }

    @Override
public List<String> getAllCategories() {
    return productRepository.findAllCategories();
}
@Override
public Page<Product> searchProducts(String keyword, Pageable pageable) {
    return productRepository.findByNameContaining(keyword, pageable);
}

@Override
public List<Product> getAllProducts(Sort sort) {
    return productRepository.findAll(sort);
}





}
