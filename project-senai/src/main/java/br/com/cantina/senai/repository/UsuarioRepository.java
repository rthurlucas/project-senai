package br.com.cantina.senai.repository;

import br.com.cantina.senai.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
}
