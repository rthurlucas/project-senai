package br.com.cantina.senai.repositorys;

import br.com.cantina.senai.model.estoque.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque,Long> {
    Optional<Estoque> findByProduto_Idproduto(Long idProduto);

}
