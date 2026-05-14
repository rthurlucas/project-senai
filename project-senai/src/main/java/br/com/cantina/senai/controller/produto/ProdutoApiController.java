package br.com.cantina.senai.controller.produto;

import br.com.cantina.senai.dto.DTOListagemProduto;
import br.com.cantina.senai.dto.DTOPedido;
import br.com.cantina.senai.dto.ItemPedidoDTO;
import br.com.cantina.senai.service.EstoqueService;
import br.com.cantina.senai.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoApiController {

    private final ProdutoService produtoService;

    public ProdutoApiController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping 
    public List<DTOListagemProduto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody ItemPedidoDTO dto) {
        return ResponseEntity.status(201).build();
    }
}
