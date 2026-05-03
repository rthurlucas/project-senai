package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.usuario.TipoUsuario;

public record DTOListagemUsuario(
        Long id,
        String nome,
        String cpf,
        TipoUsuario tipoUsuario
) {
}
