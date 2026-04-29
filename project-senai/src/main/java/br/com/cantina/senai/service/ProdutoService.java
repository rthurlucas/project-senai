package br.com.cantina.senai.service;


import br.com.cantina.senai.model.produto.Produto;
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
    public Produto criarDescricaoPedido(Produto dados){
        Produto produtoCriar = new Produto(dados);
        produtoRepository.save(produtoCriar);
        return produtoCriar;
    }

    @Transactional
    public List<Produto> listarProduto(Long id){
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto buscarProdutoPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado para buscar ID: " + id));
    }

    @Transactional
    public void excluirProduto(Long id){
        if (produtoRepository.existsById(id)){
            throw new ProdutoNotFoundException("Produto nao encontrado para excluir ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
