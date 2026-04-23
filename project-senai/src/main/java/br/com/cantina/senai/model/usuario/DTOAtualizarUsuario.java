package br.com.cantina.senai.model.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DTOAtualizarUsuario (
            @NotBlank
            String nome,

            @NotBlank
            @Pattern(regexp = "")
            String cpf


){
}
