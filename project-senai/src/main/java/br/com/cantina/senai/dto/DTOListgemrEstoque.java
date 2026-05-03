package br.com.cantina.senai.dto;

public record DTOListgemrEstoque(
        Long idEstoque,
        Long idProduto,
        Integer quantidade
) {
}
