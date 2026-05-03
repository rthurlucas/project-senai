package br.com.cantina.senai.dto;


import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTOListagemPedido(
        Long idPedido,
        Usuario idUsuario,
        Produto idProduto
) {
}
