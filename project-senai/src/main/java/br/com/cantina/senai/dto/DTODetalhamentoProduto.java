package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.produto.Produto;

public record DTODetalhamentoProduto(
        Long idProduto,
        String descricaoProduto,
        String nomeProduto
) {
    public DTODetalhamentoProduto(Produto produto){
        this(
                produto.getIdProduto(),
                produto.getDescricaoProduto(),
                produto.getNomeProduto()
        );
    }
}
