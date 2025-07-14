package br.edu.ifpr.foz.controle_de_locadora_vhs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Categoria;
import br.edu.ifpr.foz.controle_de_locadora_vhs.services.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String findAll(Model model){

        try {

            List<Categoria> categoriaList = categoriaService.findAll();
            model.addAttribute("categoriaList", categoriaList);

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

        }

        model.addAttribute("categoria", new Categoria());

        return "Categoria/categoria-list";
    }

    @GetMapping("/form")
    public String criarCategoria(Model model) {

        model.addAttribute("categoria", new Categoria());

        return "Categoria/categoria-form";
    }

    @GetMapping("/form/{id}")
    public String editarCategoria(@PathVariable Integer id, Model model) {

        Categoria categoria = null;
        
        try {

            categoria = categoriaService.findById(id);

            if (categoria == null) {

                model.addAttribute("erro", "Categoria não encontrada");

                categoria = new Categoria();
            }
        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            categoria = new Categoria();
        }

        model.addAttribute("categoria", categoria);

        return "Categoria/categoria-form";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute Categoria categoria, Model model) {

        try {

            categoriaService.save(categoria);

            return "redirect:/categorias";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            try {

                model.addAttribute("categoriaList", categoriaService.findAll());

            } catch (IllegalArgumentException ex) {

                model.addAttribute("categoriaList", List.of());

            }

            model.addAttribute("categoria", categoria);
            return "Categoria/categoria-form";
        }
    }

    @GetMapping("/excluir")
    public String excluirCategoria(@RequestParam Integer id, Model model) {

        try {

            categoriaService.delete(id);

        } catch (Exception e) {

            model.addAttribute("erro", "Erro ao excluir categoria: " + e.getMessage());
            return findAll(model);
        }
        return "redirect:/categorias";
    }
}
