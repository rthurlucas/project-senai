package br.com.cantina.senai.dto;

import br.com.cantina.senai.model.usuario.TipoUsuario;
import br.com.cantina.senai.model.usuario.Usuario;

public record DTODetalhamentoUsuario(
        Long idUsuario,
        String nome,
        String cpf,
        String telefone,
        String email,
        TipoUsuario tipoUsuario
) {
    public DTODetalhamentoUsuario(Usuario usuario){
        this(
               usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }
}
