package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTOAtualizarEstoque;
import br.com.cantina.senai.dto.DTOAtualizarUsuario;
import br.com.cantina.senai.dto.DTOCadastroUsuario;
import br.com.cantina.senai.dto.DTODetalhamentoUsuario;
import br.com.cantina.senai.model.usuario.Usuario;
import br.com.cantina.senai.model.usuario.UsuarioNotFoundException;
import br.com.cantina.senai.model.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void cadastrarUsuario(DTOCadastroUsuario dados) {
        Usuario usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios(@PathVariable Long id){
        return usuarioRepository.findAll();
    }

    public DTODetalhamentoUsuario buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
        return new DTODetalhamentoUsuario(usuario);
    }

    @Transactional
    public DTODetalhamentoUsuario atualizar(Long id, DTOAtualizarUsuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
        usuario.setNome(usuarioAtualizado.nome());
        usuario.setTelefone(usuarioAtualizado.telefone());
        usuario.setTipoUsuario(usuarioAtualizado.tipoUsuario());
        usuarioRepository.save(usuario);
        return new DTODetalhamentoUsuario(usuario);
    }

    @Transactional
    public void excluirUsuarios(Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario nao encontrado"));
        usuario.excluir();
        usuarioRepository.save(usuario);
    }
}

