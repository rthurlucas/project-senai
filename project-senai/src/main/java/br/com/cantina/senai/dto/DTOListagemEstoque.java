package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.estoque.Estoque;

public record DTOListagemEstoque(
        Long idEstoque,
        Long idProduto,
        Integer quantidade
) {
        public DTOListagemEstoque(Estoque estoque) {
                this(
                        estoque.getIdEstoque(),
                        estoque.getIdProduto().getIdProduto(),
                        estoque.getQuantidade()
                );
        }
}
