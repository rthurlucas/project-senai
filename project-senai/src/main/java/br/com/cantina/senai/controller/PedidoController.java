package br.com.cantina.senai.controller;

import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.dto.DTODetalhamentoPedido;
import br.com.cantina.senai.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<DTODetalhamentoPedido> criarPedido(
            @RequestBody DTOCadastroPedido dados) {
        DTODetalhamentoPedido pedido = pedidoService.criarPedido(dados, dados.idUsuario());
        return ResponseEntity.status(201).body(pedido);
    }
}
