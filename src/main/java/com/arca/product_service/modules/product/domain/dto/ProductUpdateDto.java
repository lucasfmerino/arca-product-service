package com.arca.product_service.modules.product.domain.dto;

import com.arca.product_service.modules.productCategory.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProductUpdateDto(
        @NotNull (message = "ID is mandatory!")
        UUID id,

        String name,
        String description,
        Double price,
        Integer quantity,
        Boolean isActive,
        List<ProductCategory> categories
)
{

}
