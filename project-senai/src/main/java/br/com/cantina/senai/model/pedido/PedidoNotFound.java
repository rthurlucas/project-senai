package br.com.cantina.senai.model.pedido;

public class PedidoNotFound extends RuntimeException {
    public PedidoNotFound(String message) {
        super(message);
    }
}
