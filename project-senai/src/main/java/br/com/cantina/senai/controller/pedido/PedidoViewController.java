package br.com.cantina.senai.controller.pedido;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoViewController {
    @GetMapping("/pedido/finalizar")
    public String exibirFinalizar() {
        return "finalizarPedido";
    }
}
