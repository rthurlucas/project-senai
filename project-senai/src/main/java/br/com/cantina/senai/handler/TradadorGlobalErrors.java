package br.com.cantina.senai.handler;

import br.com.cantina.senai.exceptions.EstoqueException;
import br.com.cantina.senai.exceptions.EstoqueNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TradadorGlobalErrors {

    @ExceptionHandler(EstoqueException.class)
    public ResponseEntity<EstoqueException> tratarEstoqueException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler(EstoqueNotFoundException.class)
    public ResponseEntity<EstoqueNotFoundException> tratarEstoqueNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
