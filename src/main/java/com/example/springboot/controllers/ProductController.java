package com.example.springboot.controllers;

import com.example.springboot.DTO.ProductDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDTO productDTO){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDTO, productModel); // copyProperties faz a conversão de DTO para model
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @DeleteMapping
    public ResponseEntity<Object> deletePrduct(@PathVariable(value = "id")UUID id, @RequestBody @Valid ProductDTO productDTO) {
         Optional<ProductModel> product0= productRepository.findById(id);
         if (product0.isEmpty()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
         }
         productRepository.delete(product0.get());
         return  ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

}
