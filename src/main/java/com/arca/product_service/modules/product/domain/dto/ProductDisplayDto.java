package com.arca.product_service.modules.product.domain.dto;

import com.arca.product_service.modules.product.domain.model.Product;
import com.arca.product_service.modules.productCategory.ProductCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductDisplayDto(
        UUID id,
        String name,
        String description,
        Double price,
        Integer quantity,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ProductCategory> categories
)
{
    public ProductDisplayDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getIsActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCategories()
        );
    }
}
