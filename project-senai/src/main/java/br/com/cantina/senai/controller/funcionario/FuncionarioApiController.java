package br.com.cantina.senai.controller.funcionario;

import br.com.cantina.senai.dto.*;
import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.service.EstoqueService;
import br.com.cantina.senai.service.PedidoService;
import br.com.cantina.senai.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioApiController {

    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;
    private final PedidoService pedidoService;

    public FuncionarioApiController(ProdutoService produtoService, EstoqueService estoqueService, PedidoService pedidoService) {
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pedidos/listar")
    public List<Pedido> listarPedidosPendentes() {
        return pedidoService.listarPedido();
    }

    @PutMapping("/estoque/{id}")
    public DTODetalhamentoEstoque atualizarEstoque(@PathVariable Long id, @RequestBody @Valid DTOAtualizarEstoque dados) {
        return estoqueService.atualizar(id, dados);
    }

    @PostMapping("/produto")
    public DTODetalhamentoProduto cadastrarProduto(@RequestBody @Valid DTOCadastroProduto dados) {
        return produtoService.criarDescricaoProduto(dados);
    }

    @GetMapping("/estoque")
    public List<DTOListagemEstoque> listarEstoque() {
        return estoqueService.listarTodos(); // Certifique-se que o service tem esse método
    }
}
