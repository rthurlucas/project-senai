package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.usuario.TipoUsuario;
import br.com.cantina.senai.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOAtualizarUsuario(
        @NotBlank
        String nome,
        @NotNull
        String telefone,
        @NotNull
        TipoUsuario tipoUsuario
) {
    public DTOAtualizarUsuario(Usuario usuario) {
        this(
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getTipoUsuario()
        );
    }
}
