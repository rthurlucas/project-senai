package br.com.cantina.senai.service;

import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.estoque.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public Estoque cadastrar(Estoque dados) {
        Estoque estoqueSalvo = new Estoque(dados);
        estoqueRepository.save(estoqueSalvo);
        return estoqueSalvo;
    }

    @Transactional
    public List<Estoque> listarEstoque(){
        Estoque estoqueSalvo = estoqueRepository.
    }
}

