package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.*;
import br.com.cantina.senai.exceptions.EstoqueNotFoundException;
import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.exceptions.PedidoNotFoundException;
import br.com.cantina.senai.repositorys.EstoqueRepository;
import br.com.cantina.senai.repositorys.PedidoRepository;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.exceptions.ProdutoNotFoundException;
import br.com.cantina.senai.repositorys.ProdutoRepository;
import br.com.cantina.senai.model.usuario.Usuario;
import br.com.cantina.senai.repositorys.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository, EstoqueRepository estoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public DTODetalhamentoPedido criarPedido(DTOCadastroPedido dados, Long idUsuario) {
        Usuario usuario = usuarioRepository.findByUsuario_id(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Pedido pedido = new Pedido(dados);
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedidoRepository.save(pedido);
        return new DTODetalhamentoPedido(pedido);
    }

    public List<Pedido> listarPedido(Long idPedido) {
        return pedidoRepository.findAll();
    }

    public DTODetalhamentoPedido buscarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado: ID" + idPedido));
        return new DTODetalhamentoPedido(pedido);
    }

    @Transactional
    public DTODetalhamentoPedido atualizarPedido(Long idPedido, DTOAtualizarPedido dados, Long idProduto) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        Produto produto = produtoRepository.findById(idProduto).orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado: " + idProduto));
        pedido.setQuantidadePedido(dados.quantidade());
        pedido.setProduto(produto);
        pedidoRepository.save(pedido);
        return new DTODetalhamentoPedido(pedido);
    }

    @Transactional
    public void excluirPedido(Long idPedido) {
        if (!pedidoRepository.existsById(idPedido)) {
            throw new PedidoNotFoundException("Esse Id nao existe: " + idPedido);
        }
        pedidoRepository.deleteById(idPedido);
    }

    public void pedidoFeito(DTOPedido dados, Long idProduto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado: " + idProduto));
        Estoque estoque = estoqueRepository.findByProduto_Idproduto(idProduto)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque nao encontrado para o produto ID: " + idProduto));
        if (dados.quantidadePedido() > estoque.getQuantidade()) {
            throw new RuntimeException("Produto sem estoque");
        }
        estoque.setQuantidade(estoque.getQuantidade() - dados.quantidadePedido());
        estoqueRepository.save(estoque);
        if (estoque.getQuantidade() == 0){
            produto.setProdutoAtivo(false);
            produtoRepository.save(produto);
        }
    }
}
