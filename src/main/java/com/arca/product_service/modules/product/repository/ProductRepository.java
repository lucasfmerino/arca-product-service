package com.arca.product_service.modules.product.repository;

import com.arca.product_service.modules.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>
{

}
