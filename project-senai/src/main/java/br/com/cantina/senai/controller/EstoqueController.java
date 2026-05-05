package br.com.cantina.senai.controller;

import br.com.cantina.senai.dto.DTOCadastroEstoque;
import br.com.cantina.senai.dto.DTOAtualizarEstoque;
import br.com.cantina.senai.dto.DTOListagemEstoque;
import br.com.cantina.senai.dto.DTODetalhamentoEstoque;
import br.com.cantina.senai.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<DTOListagemEstoque>> listarTodos() {
        List<DTOListagemEstoque> estoque = estoqueService.listarTodos();
        return ResponseEntity.ok(estoque);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODetalhamentoEstoque> buscarPorId(@PathVariable Long id) {
        DTODetalhamentoEstoque estoque = estoqueService.buscarPorId(id);
        return ResponseEntity.ok(estoque);
    }

    @PostMapping
    public ResponseEntity<DTODetalhamentoEstoque> criar(@RequestBody DTOCadastroEstoque dados) {
        DTODetalhamentoEstoque estoqueNovo = estoqueService.cadastrar(dados);
        return ResponseEntity.status(201).body(estoqueNovo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTODetalhamentoEstoque> atualizar(
            @PathVariable Long id,
            @RequestBody DTOAtualizarEstoque dados
    ) {
        DTODetalhamentoEstoque estoqueAtualizado = estoqueService.atualizar(id, dados);
        return ResponseEntity.ok(estoqueAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estoqueService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
