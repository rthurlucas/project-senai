package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.StatusPedido;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record DTODetalhamentoPedido(
        Long idPedido,
        Long idUsuario,
        String nomeUsuario,
        StatusPedido statusPedido,
        LocalDateTime dataHoraPedido,
        List<ItemPedidoDTO> itens
) {
    public record ItemPedidoDTO(Long idProduto, String nomeProduto, Integer quantidade) {}

    public DTODetalhamentoPedido(Pedido pedido) {
        this(
                pedido.getIdPedido(),
                pedido.getUsuario().getIdUsuario(),
                pedido.getUsuario().getNome(),
                pedido.getStatusPedido(),
                pedido.getDataPedido(),
                pedido.getItens().stream()
                        .map(i -> new ItemPedidoDTO(
                                i.getProduto().getIdProduto(),
                                i.getProduto().getNomeProduto(),
                                i.getQuantidade()
                        ))
                        .toList()
        );
    }
}
