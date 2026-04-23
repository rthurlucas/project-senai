package br.com.cantina.senai.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{3}")
        String cpf,

        @NotBlank
        @Pattern(regexp = "")
        String telefone,

        @NotNull
        TipoUsuario tipoUsuario
) {
}
