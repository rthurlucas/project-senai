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
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    @Transactional
    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
    }

    @Transactional
    public Estoque atualizar(Long id, Estoque dados) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        return estoque;
    }
    @Transactional
    public void excluir(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new RuntimeException("Estoque não encontrado");
        }
        estoqueRepository.deleteById(id);
    }
}