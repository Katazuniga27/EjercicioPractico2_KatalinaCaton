package com.MediCare.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.MediCare.demo.domain.Rol;
import com.MediCare.demo.service.RolService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("roles", rolService.getRoles());
        return "rol/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "rol/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return rolService.getRol(id)
                .map(rol -> {
                    model.addAttribute("rol", rol);
                    return "rol/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "El rol no fue encontrado.");
                    return "redirect:/rol/listado";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("rol") Rol rol,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "rol/formulario";
        }
        rolService.save(rol);
        redirectAttributes.addFlashAttribute("todoOk", "Rol guardado correctamente.");
        return "redirect:/rol/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            rolService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Rol eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo eliminar el rol (puede tener usuarios asociados).");
        }
        return "redirect:/rol/listado";
    }
}
