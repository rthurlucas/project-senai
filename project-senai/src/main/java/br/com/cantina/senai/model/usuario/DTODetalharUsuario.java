package br.com.cantina.senai.model.usuario;

public record DTODetalharUsuario(
        String nome,
        String cpf,
        String telefone,
        TipoUsuario tipoUsuario
) { public DTODetalharUsuario(Usuario usuario){
    this(
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getTelefone(),
            usuario.tipoUsuario
            );
}
}
