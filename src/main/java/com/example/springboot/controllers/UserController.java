package com.example.springboot.controllers;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.models.UserModel;
import com.example.springboot.repositories.UserRepository;
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
@RequestMapping("/user")
@Api(tags = "User Management")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Criar um novo usuario")
    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }

    @ApiOperation(value = "Deletar um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario deletado com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDTO userDTO) {
        Optional<UserModel> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        userRepository.delete(user0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
    }

    @ApiOperation(value = "Atualizar um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDTO userDTO) {
        Optional<UserModel> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nâo encontrado");
        }
        var userModel = user0.get();
        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @ApiOperation(value = "Obter todos os usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUser(){
        List<UserModel> user0 = this.userRepository.findAll();
        if (user0.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(user0);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiOperation(value = "Obter um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Usuario não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@ApiParam(value = "Id do usuário") @PathVariable UUID id) {
        Optional<UserModel> findById = this.userRepository.findById(id);
        if (findById.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(findById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

