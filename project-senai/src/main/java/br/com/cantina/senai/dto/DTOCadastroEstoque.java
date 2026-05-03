package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.produto.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOCadastroEstoque(
        @NotNull
        Produto idProduto,

        @NotNull
        Integer quantidade
) {
}
