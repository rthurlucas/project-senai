package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.usuario.TipoUsuario;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTODetalhamentoUsuario(
        Long id,
        String nome,
        String cpf,
        String telefone,
        TipoUsuario tipoUsuario
) {
    public DTODetalhamentoUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getTipoUsuario()
        );
    }
}
