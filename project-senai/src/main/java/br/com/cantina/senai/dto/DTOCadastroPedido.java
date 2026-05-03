package br.com.cantina.senai.dto;

public record DTOCadastroPedido(
        Long idUsuario,
        Long idProduto,
        Integer quantidade
){
}
