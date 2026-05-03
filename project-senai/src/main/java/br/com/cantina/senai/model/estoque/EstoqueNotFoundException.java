package br.com.cantina.senai.model.estoque;

public class EstoqueNotFoundException extends RuntimeException {
    public EstoqueNotFoundException(String message) {
        super(message);
    }
}
