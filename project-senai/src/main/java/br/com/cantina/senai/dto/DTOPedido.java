package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.StatusPedido;

public record DTOPedido(
        Long idProduto,
        Integer quantidadePedido,
        StatusPedido statusPedido
) {
    public DTOPedido(Pedido pedido){
        this(
                pedido.getIdPedido(),
                pedido.getQuantidadePedido(),
                pedido.getStatusPedido()
        );
    }
}
