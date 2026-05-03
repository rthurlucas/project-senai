package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTOAtualizarEstoque;
import br.com.cantina.senai.dto.DTOCadastroEstoque;
import br.com.cantina.senai.dto.DTODetalhamentoEstoque;
import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.estoque.EstoqueNotFoundException;
import br.com.cantina.senai.model.estoque.EstoqueRepository;
import br.com.cantina.senai.model.usuario.Usuario;
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
    public DTODetalhamentoEstoque cadastrar(DTOCadastroEstoque dados) {
        Estoque estoque = new Estoque(dados);
        estoqueRepository.save(estoque);
        return new DTODetalhamentoEstoque(estoque);
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public DTODetalhamentoEstoque buscarPorId(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(()-> new EstoqueNotFoundException("Estoque nao encontrado ID: " + id));
        return new DTODetalhamentoEstoque(estoque);
    }

    @Transactional
    public DTODetalhamentoEstoque atualizar(Long id, DTOAtualizarEstoque dados) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado ID: " + id));
        estoque.setQuantidade(dados.quantidade());
        estoqueRepository.save(estoque);
        return new DTODetalhamentoEstoque(estoque);
    }
    @Transactional
    public void excluir(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new RuntimeException("Estoque não encontrado");
        }
        estoqueRepository.deleteById(id);
    }
}