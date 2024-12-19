package com.arca.product_service.modules.product.domain.dto;

import com.arca.product_service.modules.productCategory.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ClientProductUpdateDto(
        @NotNull(message = "ID is mandatory!")
        UUID productId,

        Integer quantity
) {
}
