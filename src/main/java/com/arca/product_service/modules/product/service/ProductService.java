package com.arca.product_service.modules.product.service;

import com.arca.product_service.modules.product.domain.dto.ClientProductUpdateDto;
import com.arca.product_service.modules.product.domain.dto.ProductDisplayDto;
import com.arca.product_service.modules.product.domain.dto.ProductRegistrationDto;
import com.arca.product_service.modules.product.domain.dto.ProductUpdateDto;
import com.arca.product_service.modules.product.domain.model.Product;
import com.arca.product_service.modules.product.exception.ProductNotFoundException;
import com.arca.product_service.modules.product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;


    /*
     *  CREATE PRODUCT
     *
     */
    public ProductDisplayDto create(ProductRegistrationDto productRegistrationDto)
    {
        Product newProduct = new Product();
        BeanUtils.copyProperties(productRegistrationDto, newProduct);
        newProduct.setIsActive(true);

        return new ProductDisplayDto(productRepository.save(newProduct));
    }


    /*
     *  GET ALL PRODUCTS
     *
     */
    public Page<ProductDisplayDto> getAll(Pageable pageable)
    {
        return productRepository.findAll(pageable).map(ProductDisplayDto::new);
    }


    /*
     *  GET PRODUCT BY ID
     *
     */
    public ProductDisplayDto getById(UUID id)
    {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent())
        {
            return new ProductDisplayDto(productOptional.get());
        }
        else
        {
            throw new ProductNotFoundException("Product not found!");
        }
    }


    /*
     *  DELETE PRODUCT BY ID
     *
     */
    public void delete(UUID id)
    {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent())
        {
            productRepository.delete(productOptional.get());
        }
        else
        {
            throw new ProductNotFoundException("Product not found!");
        }
    }


    /*
     * UPDATE PRODUCT
     *
     */
    public ProductDisplayDto update(ProductUpdateDto productUpdateDto)
    {
        Optional<Product> productOptional = productRepository.findById(productUpdateDto.id());

        if (productOptional.isPresent())
        {
            Product productToUpdate = productOptional.get();

            if(productUpdateDto.name() != null)
            {
                productToUpdate.setName(productUpdateDto.name());
            }

            if(productUpdateDto.description() != null)
            {
                productToUpdate.setDescription(productUpdateDto.description());
            }

            if(productUpdateDto.price() != null)
            {
                productToUpdate.setPrice(productUpdateDto.price());
            }

            if(productUpdateDto.quantity() != null)
            {
                productToUpdate.setQuantity(productUpdateDto.quantity());
            }

            if(productUpdateDto.isActive() != null)
            {
                productToUpdate.setIsActive(productUpdateDto.isActive());
            }

            if(productUpdateDto.categories() != null)
            {
                productToUpdate.setCategories(productUpdateDto.categories());
            }


            return new ProductDisplayDto(productRepository.save(productToUpdate));

        }
        else
        {
            throw new ProductNotFoundException("Product not found!");
        }
    }


    /*
     * UPDATE PRODUCT BY ORDER
     *
     */
    public List<ProductDisplayDto> updateByOrder(List<ClientProductUpdateDto> listToUpdate)
    {
        List<ProductDisplayDto> updatedList = new ArrayList<>();

        for(ClientProductUpdateDto product : listToUpdate) {
            Optional<Product> productOptional = productRepository.findById(product.productId());

            if (productOptional.isPresent())
            {
                Product productToUpdate = productOptional.get();

                if (product.quantity() != null)
                {

                    productToUpdate.setQuantity(productToUpdate.getQuantity() - product.quantity());
                }

                updatedList.add(new ProductDisplayDto(productRepository.save(productToUpdate)));
            }
            else
            {
                throw new ProductNotFoundException("Product not found!");
            }
        }
        return updatedList;
    }

}
