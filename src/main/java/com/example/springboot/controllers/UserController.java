package com.example.springboot.controllers;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.models.UserModel;
import com.example.springboot.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "User Management")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Criar um novo usuario")
    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }
}
