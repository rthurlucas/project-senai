package br.com.cantina.senai.service;

import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.PedidoNotFound;
import br.com.cantina.senai.model.pedido.PedidoRepository;
import br.com.cantina.senai.model.usuario.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void criarPedido(Pedido dados){
        Pedido pedido = new Pedido(dados);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public List<Pedido> listarPedido(Long idPedido){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarPedido(Long idPedido){
        return pedidoRepository.getReferenceById(idPedido);
    }

    @Transactional
    public void excluirPedido(Long idPedido){
        if (!pedidoRepository.existsById(idPedido)){
            throw new PedidoNotFound("Esse Id nao existe: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }
}
