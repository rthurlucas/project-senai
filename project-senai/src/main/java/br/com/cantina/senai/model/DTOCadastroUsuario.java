package br.com.cantina.senai.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        String cpf
) {
}
