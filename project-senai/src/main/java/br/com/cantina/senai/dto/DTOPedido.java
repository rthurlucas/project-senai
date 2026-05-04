package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;

public record DTOPedido(
        Long idProduto,
        Integer quantidadePedido
) {
    public DTOPedido(Pedido pedido){
        this(
                pedido.getIdPedido(),
                pedido.getQuantidadePedido()
        );
    }
}
