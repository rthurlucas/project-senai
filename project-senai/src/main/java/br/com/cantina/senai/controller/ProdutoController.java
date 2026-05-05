package br.com.cantina.senai.controller;

import br.com.cantina.senai.dto.DTOCadastroProduto;
import br.com.cantina.senai.dto.DTOAtualizarProduto;
import br.com.cantina.senai.dto.DTODetalhamentoProduto;
import br.com.cantina.senai.dto.DTOListagemProduto;
import br.com.cantina.senai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping("/produto/listar")
    public ResponseEntity<List<DTOListagemProduto>> listarProdutos() {
        List<DTOListagemProduto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODetalhamentoProduto> buscarProdutoPorId(@PathVariable Long id) {
        DTODetalhamentoProduto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/produto/{nome}")
    public ResponseEntity<DTODetalhamentoProduto> buscarProdutoPorNome(@PathVariable String nome) {
        DTODetalhamentoProduto produto = produtoService.buscarProdutoPorNome(nome);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<DTODetalhamentoProduto> criarProduto(@RequestBody DTOCadastroProduto dados) {
        DTODetalhamentoProduto produto = produtoService.criarDescricaoProduto(dados);
        return ResponseEntity.status(201).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTODetalhamentoProduto> atualizarProduto(
            @PathVariable Long id,
            @RequestBody DTOAtualizarProduto dados
    ) {
        DTODetalhamentoProduto produtoAtualizado = produtoService.atualizarNomeProduto(dados, id);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}
