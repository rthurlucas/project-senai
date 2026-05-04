package br.com.cantina.senai.dto;

import jakarta.validation.constraints.NotNull;

public record DTOCadastroEstoque(
        @NotNull
        Long idProduto,

        @NotNull
        Integer quantidade
) {
}
