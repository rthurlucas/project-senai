package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.StatusPedido;
import br.com.cantina.senai.model.quantidadeProduto.QuantidadeProduto;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTOListagemPedido(
        Long idPedido,
        String nomeUsuario,
        StatusPedido statusPedido,
        Integer totalItens
) {
        public DTOListagemPedido(Pedido pedido) {
                this(
                        pedido.getIdPedido(),
                        pedido.getUsuario().getNome(),
                        pedido.getStatusPedido(),
                        pedido.getItens().stream()
                                .mapToInt(QuantidadeProduto::getQuantidade)
                                .sum()
                );
        }
}
