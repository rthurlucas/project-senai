package br.com.cantina.senai.service;

import br.com.cantina.senai.model.DTOCadastroUsuario;
import br.com.cantina.senai.model.Usuario;
import br.com.cantina.senai.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario cadastrarUsuario(@RequestBody @Valid Usuario dados){
        Usuario usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return usuarioRepository.save(dados);
    }

    @Transactional
    public List<Usuario> listarUsuarios(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return usuarioRepository.findAllById(id);
    }

    @Transactional
    public void excluirUsuarios(Long id){
        Usuario usuario = new Usuario();
        usuario.excluir();
        return usuarioRepository.delete();
    }
}
