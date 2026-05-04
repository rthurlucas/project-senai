package br.com.cantina.senai.controller;

import br.com.cantina.senai.dto.DTOCadastroProduto;
import br.com.cantina.senai.dto.DTODetalhamentoProduto;
import br.com.cantina.senai.dto.DTOListagemProduto;
import br.com.cantina.senai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<DTOListagemProduto>> listarProdutos() {
        List<DTOListagemProduto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<DTODetalhamentoProduto> buscarProdutoId(@PathVariable Long idProduto) {
        DTODetalhamentoProduto produto = produtoService.buscarProdutoPorId(idProduto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<DTODetalhamentoProduto> criarProduto(@RequestBody DTOCadastroProduto dados) {
        DTODetalhamentoProduto produto = produtoService.criarDescricaoProduto(dados);
        return ResponseEntity.ok(produto);
    }
}
