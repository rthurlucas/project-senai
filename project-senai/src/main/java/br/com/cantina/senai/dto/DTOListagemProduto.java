package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.produto.Produto;

public record DTOListagemProduto(
        Long idProduto,
        String nomeProduto,
        String descricaoProduto
) {
        public DTOListagemProduto(Produto produto) {
                this(
                        produto.getIdProduto(),
                        produto.getNomeProduto(),
                        produto.getDescricaoProduto()
                );
        }
}


