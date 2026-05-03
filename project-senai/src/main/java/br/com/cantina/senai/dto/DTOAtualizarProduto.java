package br.com.cantina.senai.dto;

import jakarta.validation.constraints.NotBlank;

public record DTOAtualizarProduto(
        @NotBlank
        String nomeProduto,
        String descricaoProduto
) {
}
