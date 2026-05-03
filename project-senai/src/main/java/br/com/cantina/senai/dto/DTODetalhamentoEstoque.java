package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.produto.Produto;

public record DTODetalhamentoEstoque(
        Long idEstoque,
        Produto idProduto,
        Integer quantidade
) {
    public DTODetalhamentoEstoque(Estoque estoque){
        this(
                estoque.getIdEstoque(),
                estoque.getIdProduto(),
                estoque.getQuantidade()
        );
    }
}
