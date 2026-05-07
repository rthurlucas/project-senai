package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.*;
import br.com.cantina.senai.exceptions.ProdutoNotFoundException;
import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.exceptions.EstoqueNotFoundException;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.repositorys.EstoqueRepository;
import br.com.cantina.senai.repositorys.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    //Crud Estoque

    @Transactional
    public DTODetalhamentoEstoque cadastrar(DTOCadastroEstoque dados) {
        Produto produto = produtoRepository.findById(dados.idProduto())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidade(dados.quantidade());
        estoqueRepository.save(estoque);
        return new DTODetalhamentoEstoque(estoque);
    }

    public List<DTOListagemEstoque> listarTodos() {
            return estoqueRepository.findAll()
                    .stream()
                    .map(DTOListagemEstoque::new)
                    .toList();
    }

    public DTODetalhamentoEstoque buscarPorId(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(()-> new EstoqueNotFoundException("Estoque nao encontrado ID: " + id));
        return new DTODetalhamentoEstoque(estoque);
    }

    @Transactional
    public DTODetalhamentoEstoque atualizar(Long id, DTOAtualizarEstoque dados) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque não encontrado ID: " + id));
        if (dados.quantidade() != null){
            estoque.setQuantidade(dados.quantidade());
        }
        return new DTODetalhamentoEstoque(estoque);
    }

    @Transactional
    public void excluir(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new EstoqueNotFoundException("Estoque não encontrado");
        }
        estoqueRepository.deleteById(id);
    }
}