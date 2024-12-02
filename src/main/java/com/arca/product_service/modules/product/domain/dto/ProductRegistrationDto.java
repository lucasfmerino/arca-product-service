package com.arca.product_service.modules.product.domain.dto;

import com.arca.product_service.modules.productCategory.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRegistrationDto(
        @NotBlank(message = "The name field is mandatory!")
        String name,

        String description,

        @NotNull(message = "The price field is mandatory!")
        Double price,

        @NotNull(message = "The quantity field is mandatory!")
        Integer quantity,

        @NotNull
        List<ProductCategory> categories
)
{
}
