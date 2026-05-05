package br.com.cantina.senai.service;

import br.com.cantina.senai.dto.*;
import br.com.cantina.senai.exceptions.EstoqueNotFoundException;
import br.com.cantina.senai.model.estoque.Estoque;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.exceptions.PedidoNotFoundException;
import br.com.cantina.senai.model.pedido.StatusPedido;
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

    //Repositorys

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueRepository estoqueRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository, EstoqueRepository estoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueRepository = estoqueRepository;
    }

    //Crud do pedido

    @Transactional
    public DTODetalhamentoPedido criarPedido(DTOCadastroPedido dados, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Pedido pedido = new Pedido(dados);
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.CRIADO);
        pedidoRepository.save(pedido);
        return new DTODetalhamentoPedido(pedido);
    }

    public List<Pedido> listarPedido(Object o) {
        return pedidoRepository.findAll()
                .stream()
                .filter(Pedido -> Pedido.getStatusPedido() == StatusPedido.CRIADO || Pedido.getStatusPedido() == StatusPedido.EM_PREPARACAO)
                .toList();
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
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        pedido.setStatusPedido(StatusPedido.CANCELADO);
        pedidoRepository.deleteById(idPedido);
    }

    @Transactional
    public void pedidoFeito(DTOPedido dados, Long idProduto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado: " + idProduto));
        Estoque estoque = estoqueRepository.findByProduto_IdProduto(idProduto)
                .orElseThrow(() -> new EstoqueNotFoundException("Estoque nao encontrado para o produto ID: " + idProduto));
        if (dados.quantidadePedido() > estoque.getQuantidade()) {
            throw new RuntimeException("Produto sem estoque");
        }
        if (dados.quantidadePedido() <= 0) {
            throw new RuntimeException("Por favor informe algum produto");
        }
        estoque.setQuantidade(estoque.getQuantidade() - dados.quantidadePedido());
        estoqueRepository.save(estoque);
        if (estoque.getQuantidade() <= 0){
            produto.setProdutoAtivo(false);
            produtoRepository.save(produto);
        }
    }

    //Enums de Status do Pedido

    @Transactional
    public void marcarPedidoPronto(Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                () -> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        pedido.setStatusPedido(StatusPedido.FINALIZADO);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void marcarPedidoEmPreparacao(Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                () -> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        pedido.setStatusPedido(StatusPedido.EM_PREPARACAO);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void marcarPedidoFinalizado(Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                () -> new PedidoNotFoundException("Pedido nao encontrado ID: " + idPedido));
        pedido.setStatusPedido(StatusPedido.FINALIZADO);
        pedidoRepository.save(pedido);
    }
}
