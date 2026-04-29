package br.com.cantina.senai.service;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.PedidoNotFoundException;
import br.com.cantina.senai.model.pedido.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Pedido criarPedido(Pedido dados){
        Pedido pedido = new Pedido(dados);
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional
    public List<Pedido> listarPedido(Long idPedido){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarPedido(Long idPedido){
        return pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado: ID" + idPedido));
    }

    @Transactional
    public void excluirPedido(Long idPedido){
        if (!pedidoRepository.existsById(idPedido)){
            throw new PedidoNotFoundException("Esse Id nao existe: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }
}
