package br.com.cantina.senai.controller;

import br.com.cantina.senai.service.ProdutoService;
import br.com.cantina.senai.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    @Autowired
    public HomeController(ProdutoService produtoService, UsuarioService usuarioService) {
        this.produtoService = produtoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/home")
    public String exibirHome(Model model) {
        return "home";
    }
}