package br.edu.ifpr.foz.controle_de_locadora_vhs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Usuario;
import br.edu.ifpr.foz.controle_de_locadora_vhs.services.AutenticacaoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AutenticacaoController {
    
    @Autowired
    AutenticacaoService autenticacaoService;

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession sessao, RedirectAttributes model){

        try {
            Usuario usuario = autenticacaoService.login(email, senha);
            //já deu certo
            sessao.setAttribute("usuarioLogado", usuario);
            return "redirect:/home";

        } catch (Exception e) {
            model.addFlashAttribute("mensagem", e.getMessage());
            model.addFlashAttribute("email", email);
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession sessao) {
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        model.addAttribute("usuario", usuario);
        return "home";
    }

    @GetMapping("/cadastro")
    public String abrirFormularioCadastro(Model model, @ModelAttribute("mensagem") String mensagem) {
        if (!model.containsAttribute("nome")) {
            model.addAttribute("nome", "");
        }
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        model.addAttribute("usuario", new Usuario());

        if (mensagem != null && !mensagem.isEmpty()) {
            model.addAttribute("mensagem", mensagem);
        }
        return "cadastro";
    }

    // Rota para processar o POST do cadastro
    @PostMapping("/cadastro")
    public String cadastrarUsuario(@RequestParam String nome,
                                   @RequestParam String email,
                                   @RequestParam String senha,
                                   RedirectAttributes model) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            autenticacaoService.cadastrar(usuario);
            return "redirect:/login";
        } catch (Exception e) {
            model.addFlashAttribute("mensagem", e.getMessage());
            model.addFlashAttribute("nome", nome);
            model.addFlashAttribute("email", email); 
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession sessao){
        sessao.invalidate();
        return "redirect:/login";
    }

}
