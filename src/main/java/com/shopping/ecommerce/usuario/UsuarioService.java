package com.shopping.ecommerce.usuario;

import com.shopping.ecommerce.exception.usuarioexception.UsernameExistenteException;
import com.shopping.ecommerce.exception.usuarioexception.UsuarioNaoEncontradoException;
import com.shopping.ecommerce.usuario.dto.request.UsuarioAlteracaoRequestDto;
import com.shopping.ecommerce.usuario.dto.request.UsuarioCadastroRequestDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void salvarUsuario(UsuarioCadastroRequestDto userDto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(userDto.username());
        usuario.setPassword(passwordEncoder.encode(userDto.password()));
        usuario.setRole(userDto.role());

        repository.save(usuario);
    }

    @Transactional
    public void alterarUsuario(UsuarioAlteracaoRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuarioLogado = authentication.getName();

        if (!usuarioLogado.equals(dto.usernameAtual())) {
            throw new AccessDeniedException("Você só pode alterar sua própria conta.");
        }

        Usuario usuario = repository.findByUsername(dto.usernameAtual())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.usernameAtual()));

        if (dto.novoUsername() != null && !dto.novoUsername().isBlank()) {
            repository.findByUsername(dto.novoUsername())
                    .ifPresent(u -> {throw new UsernameExistenteException(dto.novoUsername());});
            usuario.setUsername(dto.novoUsername());
        }

        if (dto.novoPassword() != null && !dto.novoPassword().isBlank()){
            usuario.setPassword(passwordEncoder.encode(dto.novoPassword()));
        }

        repository.save(usuario);
    }

    @Transactional
    public void deletarUsuarioPorId(Long id) {
        repository.deleteById(id);
    }

}
