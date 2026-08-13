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
import com.MediCare.demo.domain.CitaMedica;
import com.MediCare.demo.service.CitaMedicaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cita")
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    public CitaMedicaController(CitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("citas", citaMedicaService.getCitas());
        return "cita/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cita", new CitaMedica());
        return "cita/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return citaMedicaService.getCita(id)
                .map(cita -> {
                    model.addAttribute("cita", cita);
                    return "cita/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "La cita no fue encontrada.");
                    return "redirect:/cita/listado";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cita") CitaMedica cita,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cita/formulario";
        }
        citaMedicaService.save(cita);
        redirectAttributes.addFlashAttribute("todoOk", "Cita medica guardada correctamente.");
        return "redirect:/cita/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        citaMedicaService.delete(id);
        redirectAttributes.addFlashAttribute("todoOk", "Cita medica eliminada correctamente.");
        return "redirect:/cita/listado";
    }
}
