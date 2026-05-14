package br.com.cantina.senai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOCadastroProduto(
        @NotBlank
        String descricaoProduto,
        @NotBlank
        String nomeProduto
) {
}
