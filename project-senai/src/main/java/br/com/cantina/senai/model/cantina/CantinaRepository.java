package br.com.cantina.senai.model.cantina;

import br.com.cantina.senai.dto.DTOCadastroProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CantinaRepository extends JpaRepository<Cantina, Long> {
}
