package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTODetalhamentoPedido(
        Long idPedido,
        Usuario idusuario,
        Produto idProduto
) {
    public DTODetalhamentoPedido(Pedido pedido){
        this(
                pedido.getIdPedido(),
                pedido.getUsuario(),
                pedido.getProduto()
        );
    }
}
