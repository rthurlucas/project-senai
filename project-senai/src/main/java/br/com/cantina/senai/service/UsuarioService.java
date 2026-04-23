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
    public List<Usuario> listarUsuarios(@PathVariable Long id){
        return usuarioRepository.findAll();
    }

    @Transactional
    public void excluirUsuarios(Long idUsuario){
        if (!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNotFound("Usuario nao encontrado");
        }
        Usuario usuario = usuarioRepository.getReferenceById(idUsuario);
        usuario.excluir();
        usuarioRepository.save(usuario);
    }
}
