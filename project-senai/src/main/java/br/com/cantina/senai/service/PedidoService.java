package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.DTOAtualizarEstoque;
import br.com.cantina.senai.dto.DTOAtualizarPedido;
import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.dto.DTODetalhamentoPedido;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.pedido.PedidoNotFoundException;
import br.com.cantina.senai.model.pedido.PedidoRepository;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.produto.ProdutoNotFoundException;
import br.com.cantina.senai.model.produto.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,  ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public DTODetalhamentoPedido criarPedido(DTOCadastroPedido dados){
        Pedido pedido = new Pedido(dados);
        pedidoRepository.save(pedido);
        return new DTODetalhamentoPedido(pedido);
    }


    public List<Pedido> listarPedido(Long idPedido){
        return pedidoRepository.findAll();
    }


    public DTODetalhamentoPedido buscarPedido(Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado: ID" + idPedido));
        return new DTODetalhamentoPedido(pedido);
    }

    @Transactional
    public DTODetalhamentoPedido atualizarPedido(Long idPedido, DTOAtualizarPedido dados, Long idProduto){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(()-> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        Produto produto = produtoRepository.findById(idProduto).orElseThrow(()-> new ProdutoNotFoundException("Produto nao encontrado: " + idProduto));
        pedido.setQuantidade(dados.quantidade());
        pedido.setProduto(produto);
        pedidoRepository.save(pedido);
        return new  DTODetalhamentoPedido(pedido);
    }

    @Transactional
    public void excluirPedido(Long idPedido){
        if (!pedidoRepository.existsById(idPedido)){
            throw new PedidoNotFoundException("Esse Id nao existe: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }
}
