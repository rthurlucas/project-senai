package br.com.cantina.senai.service;

import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void criarPedido(@RequestBody @Valid Pedido dados){
        Pedido pedido = new Pedido(dados);
        pedidoRepository.save(pedido);
    }
}
