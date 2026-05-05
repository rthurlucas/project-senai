package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.StatusPedido;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;

import java.time.LocalDateTime;

public record DTODetalhamentoPedido(
        Long idPedido,
        Usuario idUsuario,
        Produto idProduto,
        StatusPedido statusPedido,
        LocalDateTime dataHoraPedido
) {
    public DTODetalhamentoPedido(Pedido pedido){
        this(
                pedido.getIdPedido(),
                pedido.getUsuario(),
                pedido.getProduto(),
                pedido.getStatusPedido(),
                pedido.getDataPedido()
        );
    }
}
