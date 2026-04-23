package br.com.cantina.senai.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    List<Usuario> findAllById(Long id);


}
