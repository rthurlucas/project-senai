package br.com.cantina.senai.service;

import br.com.cantina.senai.model.usuario.Usuario;
import br.com.cantina.senai.model.usuario.UsuarioNotFoundException;
import br.com.cantina.senai.model.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario cadastrarUsuario(Usuario dados){
        Usuario usuario = new Usuario(dados);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public List<Usuario> listarUsuarios(Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return usuarioRepository.findAllById(id);
    }

    @Transactional
    public void excluirUsuarios(Long idUsuario){
        if (!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNotFoundException("Usuario nao encontrado");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));
        usuario.excluir();
        usuarioRepository.save(usuario);
    }
    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setCpf(usuarioAtualizado.getCpf());
        return usuarioRepository.save(usuario);
    }


}

