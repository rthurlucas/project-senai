package br.com.cantina.senai.controller.usuario;

import br.com.cantina.senai.dto.DTOCadastroUsuario;
import br.com.cantina.senai.dto.DTODetalhamentoUsuario;
import br.com.cantina.senai.model.usuario.TipoUsuario;
import br.com.cantina.senai.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioVIewController {

    private final UsuarioService usuarioService;

    public UsuarioVIewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastrar")
    public String exibirCadastro(Model model) {
        // Isso aqui garante que o formulário não comece quebrado
        model.addAttribute("dto", new DTOCadastroUsuario("", "", "", "", br.com.cantina.senai.model.usuario.TipoUsuario.USUARIO));
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String processarCadastro(@ModelAttribute("dto") DTOCadastroUsuario dto) {
        // 1. Salva no banco (Isso resolve o problema do NULL se o DTO estiver com os nomes certos)
        usuarioService.cadastrarUsuario(dto);

        // 2. Redireciona para a Home (Isso resolve o erro 404 de POST /home)
        return "redirect:/home";
    }
}
