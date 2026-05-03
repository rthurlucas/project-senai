package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.cantina.Cantina;
import br.com.cantina.senai.model.produto.Produto;

public record DTODetalhamentoCantina(
        Long idCantina,
        Produto idProduto,
        boolean disponivel
) {
    public DTODetalhamentoCantina(Cantina cantina){
        this(
                cantina.getId(),
                cantina.getIdProduto(),
                cantina.isDisponivel()
        );
    }
}
