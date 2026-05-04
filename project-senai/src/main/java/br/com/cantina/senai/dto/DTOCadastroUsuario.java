package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroUsuario(
        @NotBlank
        String nome,

        @NotNull
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
        String cpf,

        @NotNull
        @Pattern(regexp = "\\d{2} \\d{5}-\\d{4}")
        String telefone,

        @Email
        @NotBlank
        String email,

        @NotNull
        TipoUsuario tipoUsuario
) {
}
