package br.com.cantina.senai.handler;

import br.com.cantina.senai.exceptions.*;
import br.com.cantina.senai.model.produto.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TradadorGlobalErrors {

    //Tratando todas as exceptions

    @ExceptionHandler
    public ResponseEntity<UsuarioNotFoundException> tratarUsuarioNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(EstoqueException.class)
    public ResponseEntity<EstoqueException> tratarEstoqueException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(EstoqueNotFoundException.class)
    public ResponseEntity<EstoqueNotFoundException> tratarEstoqueNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ProdutoNameException.class)
    public ResponseEntity<ProdutoNameException> tratarProdutoNameException(Exception e){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ProdutoNotFoundException> tratarProdutoNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<PedidoNotFoundException> tratarPedidoNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
