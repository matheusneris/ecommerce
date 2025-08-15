package com.shopping.ecommerce.usuario;

import com.shopping.ecommerce.usuario.enums.UsuarioPapelEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "TB_USUARIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long                id;
    private String              username;
    private String              password;
    @Enumerated(EnumType.STRING)
    private UsuarioPapelEnum    role;

}
