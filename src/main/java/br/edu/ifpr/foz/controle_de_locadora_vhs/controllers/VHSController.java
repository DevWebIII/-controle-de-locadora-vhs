package br.edu.ifpr.foz.controle_de_locadora_vhs.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.TapeStatus;
import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.VHS;
import br.edu.ifpr.foz.controle_de_locadora_vhs.services.CategoriaService;
import br.edu.ifpr.foz.controle_de_locadora_vhs.services.VHSService;

@Controller
@RequestMapping("/vhs")
public class VHSController {

    @Autowired
    private VHSService vhsService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarVHS(Model model) {
        try {
            List<VHS> lista = vhsService.findAll();
            model.addAttribute("vhsList", lista);
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "VHS/vhs-list";
    }

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {

        model.addAttribute("vhs", new VHS());
        model.addAttribute("categorias", categoriaService.findAll());

        return "VHS/vhs-form";
    }

    @PostMapping("/save")
    public String salvarVHS(@ModelAttribute VHS vhs, Model model) {

        try {
            if (vhs.getDataCadastro() == null) {
                vhs.setDataCadastro(LocalDate.now());
            }
            vhsService.save(vhs);

            return "redirect:/vhs";

        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("vhs", vhs);
            model.addAttribute("categorias", categoriaService.findAll());

            return "VHS/vhs-form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editarVHS(@PathVariable Integer id, Model model) {

        VHS vhs = null;
        
        try {
            vhs = vhsService.findById(id);

        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            vhs = new VHS();
        }

        model.addAttribute("vhs", vhs);
        model.addAttribute("categorias", categoriaService.findAll());

        return "VHS/vhs-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVHS(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            VHS vhs = vhsService.findById(id);

            if (vhs.getStatus() != TapeStatus.INDISPONIVEL) {
                ra.addFlashAttribute("erro", "Apenas fitas indisponíveis podem ser excluídas");
                return "redirect:/vhs";
            }

            vhsService.delete(vhs);
            ra.addFlashAttribute("mensagem", "Fita excluída!");

        } catch (IllegalArgumentException e) {

            ra.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/vhs";
    }
}
