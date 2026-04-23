package br.com.cantina.senai.model.usuario;

public class UsuarioNotFound extends RuntimeException {
    public UsuarioNotFound(String message) {
        super(message);
    }
}
