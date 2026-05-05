package br.com.cantina.senai.controller;

import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.dto.DTOAtualizarPedido;
import br.com.cantina.senai.dto.DTODetalhamentoPedido;
import br.com.cantina.senai.dto.DTOPedido;
import br.com.cantina.senai.service.PedidoService;
import br.com.cantina.senai.model.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedido(null);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODetalhamentoPedido> buscarPedido(@PathVariable Long id) {
        DTODetalhamentoPedido pedido = pedidoService.buscarPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<DTODetalhamentoPedido> criarPedido(
            @RequestBody DTOCadastroPedido dados,
            @RequestParam(required = true) Long idUsuario
    ) {
        DTODetalhamentoPedido pedidoNovo = pedidoService.criarPedido(dados, idUsuario);
        return ResponseEntity.status(201).body(pedidoNovo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTODetalhamentoPedido> atualizarPedido(
            @PathVariable Long id,
            @RequestBody DTOAtualizarPedido dados,
            @RequestParam(required = true) Long idProduto
    ) {
        DTODetalhamentoPedido pedidoAtualizado = pedidoService.atualizarPedido(id, dados, idProduto);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Void> finalizarPedido(
            @RequestBody DTOPedido dados,
            @RequestParam(required = true) Long idProduto
    ) {
        pedidoService.pedidoFeito(dados, idProduto);
        return ResponseEntity.status(200).build();
    }
}
