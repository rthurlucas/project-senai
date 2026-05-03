package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTOAtualizarProduto;
import br.com.cantina.senai.dto.DTOCadastroProduto;
import br.com.cantina.senai.dto.DTODetalhamentoProduto;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.produto.ProdutoNameException;
import br.com.cantina.senai.model.produto.ProdutoNotFoundException;
import br.com.cantina.senai.model.produto.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public DTODetalhamentoProduto criarDescricaoProduto(DTOCadastroProduto dados){
        Produto produto = new Produto(dados);
        produtoRepository.save(produto);
        return new DTODetalhamentoProduto(produto);
    }

    public List<Produto> listarProduto(Long id){
        return produtoRepository.findAll();
    }

    public DTODetalhamentoProduto buscarProdutoPorId(Long idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado para buscar ID: " + idProduto));
        return new DTODetalhamentoProduto(produto);
    }

    public DTODetalhamentoProduto buscarProdutoPorNome(String nomeProduto){
        Produto produto = produtoRepository.findByNomeProduto(nomeProduto).orElseThrow(() -> new ProdutoNameException("Produto nao encontrado pelo nome: " + nomeProduto));
        return new DTODetalhamentoProduto(produto);
    }

    public DTODetalhamentoProduto atualizarNomeProduto(DTOAtualizarProduto dados, Long idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado pelo ID: " + idProduto));
        if (dados.descricaoProduto() != null){
            produto.setDescricaoProduto(dados.descricaoProduto());
        }
       produto.setNomeProduto(dados.nomeProduto());
        return new  DTODetalhamentoProduto(produto);
    }

    @Transactional
    public void excluirProduto(Long id){
        if (produtoRepository.existsById(id)){
            throw new ProdutoNotFoundException("Produto nao encontrado para excluir ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
