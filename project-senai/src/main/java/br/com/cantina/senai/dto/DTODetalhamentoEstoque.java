package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.estoque.Estoque;

public record DTODetalhamentoEstoque(
        Long idEstoque,
        Long idProduto,
        Integer quantidade
) {
    public DTODetalhamentoEstoque(Estoque estoque){
        this(
                estoque.getIdEstoque(),
                estoque.getIdProduto().getIdProduto(),
                estoque.getQuantidade()
        );
    }
}
