package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTOAtualizarUsuario;
import br.com.cantina.senai.dto.DTOCadastroUsuario;
import br.com.cantina.senai.dto.DTOListagemUsuario;
import br.com.cantina.senai.dto.DTODetalhamentoUsuario;
import br.com.cantina.senai.model.usuario.Usuario;
import br.com.cantina.senai.exceptions.UsuarioNotFoundException;
import br.com.cantina.senai.repositorys.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Crud Usuario

    @Transactional
    public DTODetalhamentoUsuario cadastrarUsuario(DTOCadastroUsuario dados) {
        Usuario usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return new DTODetalhamentoUsuario(usuario);
    }

    public List<DTOListagemUsuario> listarUsuarios(Object o){
        return usuarioRepository.findAll().stream()
                .map(DTOListagemUsuario::new)
                .toList();
    }

    public DTODetalhamentoUsuario buscarUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado ID: " + idUsuario));
        return new DTODetalhamentoUsuario(usuario);
    }

    public DTODetalhamentoUsuario buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com email: " + email));
        return new DTODetalhamentoUsuario(usuario);
    }

    @Transactional
    public DTODetalhamentoUsuario atualizar(Long idUsuario, DTOAtualizarUsuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado ID: " + idUsuario));
        if (usuarioAtualizado.nome() != null) {
            usuario.setNome(usuarioAtualizado.nome());
        }
        if (usuarioAtualizado.telefone() != null) {
            usuario.setTelefone(usuarioAtualizado.telefone());
        }
        if (usuarioAtualizado.email() != null) {
            usuario.setEmail(usuarioAtualizado.email());
        }
        if (usuarioAtualizado.tipoUsuario() != null) {
            usuario.setTipoUsuario(usuarioAtualizado.tipoUsuario());
        }
        usuarioRepository.save(usuario);
        return new DTODetalhamentoUsuario(usuario);
    }

    @Transactional
    public void excluirUsuarios(Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario nao encontrado"));
        usuarioRepository.delete(usuario);
    }
}