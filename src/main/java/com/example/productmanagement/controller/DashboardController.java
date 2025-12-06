package com.example.productmanagement.controller;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showDashboard(Model model) {

        // Lấy toàn bộ sản phẩm
        List<Product> products = productService.getAllProducts();

        // 1. Tổng số sản phẩm
        int totalProducts = products.size();

        // 2. Tổng số category
        long totalCategories = products.stream()
                .map(Product::getCategory)
                .filter(Objects::nonNull)
                .distinct()
                .count();

        // 3. Tổng giá trị kho
        double totalInventoryValue = products.stream()
                .mapToDouble(p -> p.getPrice().doubleValue() * p.getQuantity())
                .sum();

        // 4. Giá trung bình
        double averagePrice = products.stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .average()
                .orElse(0);

        // 5. Product theo category (để chart)
        Map<String, Long> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        // 6. Low stock (Qty < 10)
        List<Product> lowStock = products.stream()
                .filter(p -> p.getQuantity() < 10)
                .collect(Collectors.toList());

        // 7. Recent products (Last 5)
        // Nếu bạn **không có createdAt**, thì sort theo id mới nhất
        List<Product> recentProducts = products.stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .limit(5)
                .toList();

        // Add sang model
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalInventoryValue", totalInventoryValue);
        model.addAttribute("averagePrice", averagePrice);
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("lowStock", lowStock);
        model.addAttribute("recentProducts", recentProducts);

        return "dashboard";
    }
}
