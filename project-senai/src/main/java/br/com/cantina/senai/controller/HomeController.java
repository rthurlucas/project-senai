package br.com.cantina.senai.controller;

import br.com.cantina.senai.service.ProdutoService;
import br.com.cantina.senai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    @Autowired
    public HomeController(UsuarioService usuarioService, ProdutoService produtoService) {
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios(null));
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "home";
    }
}