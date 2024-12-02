package com.arca.product_service.modules.product.controller;

import com.arca.product_service.modules.product.domain.dto.ProductDisplayDto;
import com.arca.product_service.modules.product.domain.dto.ProductRegistrationDto;
import com.arca.product_service.modules.product.domain.dto.ProductUpdateDto;
import com.arca.product_service.modules.product.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/products")
public class ProductController
{
    @Autowired
    private ProductService productService;


    /*
     * REGISTER
     *
     */
    @PostMapping()
    @Transactional
    public ResponseEntity<ProductDisplayDto> register(
            @RequestBody @Valid ProductRegistrationDto productRegistrationDto,
            UriComponentsBuilder uriComponentsBuilder
    )
    {
        ProductDisplayDto newProjectDisplayDto = productService.create(productRegistrationDto);

        URI uri = uriComponentsBuilder.path("/api/products/{id}").buildAndExpand(newProjectDisplayDto.id()).toUri();

        return ResponseEntity.created(uri).body(newProjectDisplayDto);
    }


    /*
     * GET ALL PRODUCTS
     *
     */
    @GetMapping()
    public ResponseEntity<Page<ProductDisplayDto>> getAll(
            @PageableDefault(size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable
    )
    {
        return ResponseEntity.ok(productService.getAll(pageable));
    }


    /*
     * GET PRODUCT BY ID
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDisplayDto> getById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(productService.getById(id));
    }


    /*
     * DELETE PRODUCT BY ID
     *
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID id)
    {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }


    /*
     * UPDATE PRODUCT
     *
     */
    @PutMapping()
    @Transactional
    public ResponseEntity<ProductDisplayDto> update(@RequestBody @Valid ProductUpdateDto productUpdateDto)
    {
        return ResponseEntity.ok(productService.update(productUpdateDto));
    }

}
