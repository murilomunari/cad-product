package com.example.springboot.models;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@ToString
@ApiModel
public class UserModel implements Serializable {
    private static final long serialVersion = 1L;

    private UUID idUser;

    private String name;

    private String email;

    private String cnpj;
}
