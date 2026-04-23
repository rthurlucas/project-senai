package br.com.cantina.senai.service;

import br.com.cantina.senai.model.usuario.Usuario;
import br.com.cantina.senai.model.usuario.UsuarioNotFound;
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
    public void excluirUsuarios(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new UsuarioNotFound("Usuario nao encontrado");
        }
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.excluir();
        usuarioRepository.save(usuario);
    }
    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFound("Usuário não encontrado"));
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setCpf(usuarioAtualizado.getCpf());
        return usuarioRepository.save(usuario);
    }


}

