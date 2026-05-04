package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTOListagemPedido(
        Long idPedido,
        Usuario idUsuario,
        Long idProduto
) {
        public DTOListagemPedido(Pedido pedido) {
                this(
                        pedido.getIdPedido(),
                        pedido.getUsuario(),
                        pedido.getProduto().getIdProduto()
                );
        }
}
