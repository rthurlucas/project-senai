package br.com.cantina.senai.dto;

public record ItemPedidoDTO(
        Long idProduto,
        String nomeProduto,
        Integer quantidade
) {
}
