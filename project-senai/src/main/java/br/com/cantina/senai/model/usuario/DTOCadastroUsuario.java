package br.com.cantina.senai.model.usuario;

import jakarta.validation.constraints.NotBlank;

public record DTOCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        String cpf
) {
}
