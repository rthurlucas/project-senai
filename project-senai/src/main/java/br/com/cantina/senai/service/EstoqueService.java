package br.com.cantina.senai.service;

import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.estoque.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    public List<Estoque> listarEstoque() {
        return estoqueRepository.findAll();
    }

    public void atualizarEstoque(Estoque estoque){

    }
}
