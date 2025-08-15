package com.shopping.ecommerce.usuario;

import com.shopping.ecommerce.usuario.dto.request.UsuarioAlteracaoRequestDto;
import com.shopping.ecommerce.usuario.dto.request.UsuarioCadastroRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioCadastroRequestDto userDto) {
        service.salvarUsuario(userDto);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity deletarUsuario(@PathVariable Long id) {
        service.deletarUsuarioPorId(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/alterar")
    public ResponseEntity alterarUsuario(@RequestBody @Valid UsuarioAlteracaoRequestDto dto) {
        service.alterarUsuario(dto);
        return ResponseEntity.ok().build();
    }
}
