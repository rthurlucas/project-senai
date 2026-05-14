package br.com.cantina.senai.controller.funcionario;

import br.com.cantina.senai.service.EstoqueService;
import br.com.cantina.senai.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioViewController {

    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;

    public FuncionarioViewController(ProdutoService produtoService, EstoqueService estoqueService) {
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public String exibirPainel(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        model.addAttribute("estoques", estoqueService.listarTodos());
        return "funcionario";
    }
}
