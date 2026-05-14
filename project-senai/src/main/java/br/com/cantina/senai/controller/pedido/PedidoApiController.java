package br.com.cantina.senai.controller.pedido;

import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.dto.DTODetalhamentoPedido;
import br.com.cantina.senai.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoApiController {
    private final PedidoService pedidoService;

    public PedidoApiController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public DTODetalhamentoPedido criar(@RequestBody DTOCadastroPedido dados) {
        return pedidoService.criarPedido(dados, 1L);
    }
}
