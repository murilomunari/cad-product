package com.example.springboot.controllers;

import com.example.springboot.DTO.ProductDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Api(tags = "Product Management")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @ApiOperation(value = "Criar um novo produto")
    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDTO productDTO){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @ApiOperation(value = "Deletar um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id")UUID id, @RequestBody @Valid ProductDTO productDTO) {
        Optional<ProductModel> product0= productRepository.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        productRepository.delete(product0.get());
        return  ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

    @ApiOperation(value = "Atualizar um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductDTO productDTO){
        Optional<ProductModel> product0= productRepository.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productDTO, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @ApiOperation(value = "Obter todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de produtos retornada com sucesso"),
            @ApiResponse(code = 404, message = "Nenhum produto encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = this.productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
    }

    @ApiOperation(value = "Obter um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> findById(@ApiParam(value = "ID do produto") @PathVariable UUID id) {
        Optional<ProductModel> productOptional = this.productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(productOptional.get());
        }
    }
}
