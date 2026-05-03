package br.com.cantina.senai.model.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNomeProduto(String nomeProduto);
}
