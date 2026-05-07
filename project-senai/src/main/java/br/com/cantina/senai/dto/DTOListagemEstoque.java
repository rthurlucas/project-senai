package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.estoque.Estoque;

public record DTOListagemEstoque(
        Long idEstoque,
        Long idProduto,
        String nomeProduto,
        String descricaoProduto,
        Integer quantidade
) {
        public DTOListagemEstoque(Estoque estoque) {
                this(
                        estoque.getIdEstoque(),
                        estoque.getProduto().getIdProduto(),
                        estoque.getProduto().getNomeProduto(),
                        estoque.getProduto().getDescricaoProduto(),
                        estoque.getQuantidade()
                );
        }
}
