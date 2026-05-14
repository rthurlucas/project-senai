package br.com.cantina.senai.controller.home;

import br.com.cantina.senai.service.ProdutoService;
import br.com.cantina.senai.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    public HomeController(UsuarioService usuarioService, ProdutoService produtoService) {
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
    }

   @GetMapping("/home")
    public String exibirHome(Model model) {
       model.addAttribute("produtos", produtoService.listarProdutos());
       return "home";
   }
}