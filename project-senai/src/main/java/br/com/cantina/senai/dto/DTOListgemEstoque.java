package br.com.cantina.senai.dto;

public record DTOListgemEstoque(
        Long idEstoque,
        Long idProduto,
        Integer quantidade
) {
}
