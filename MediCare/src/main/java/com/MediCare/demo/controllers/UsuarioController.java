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
import com.MediCare.demo.domain.Usuario;
import com.MediCare.demo.service.RolService;
import com.MediCare.demo.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("usuarios", usuarioService.getUsuarios());
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.getRoles());
        return "usuario/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return usuarioService.getUsuario(id)
                .map(usuario -> {
                    usuario.setPassword("");
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("roles", rolService.getRoles());
                    return "usuario/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "El usuario no fue encontrado.");
                    return "redirect:/usuario/listado";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        boolean esNuevo = (usuario.getId() == null);
        // La contrasena solo es obligatoria al crear un usuario nuevo
        if (esNuevo && (usuario.getPassword() == null || usuario.getPassword().isBlank())) {
            bindingResult.rejectValue("password", "NotBlank", "La contrasena es obligatoria");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", rolService.getRoles());
            return "usuario/formulario";
        }
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("todoOk",
                esNuevo ? "Usuario creado. Se envio un correo de bienvenida." : "Usuario actualizado correctamente.");
        return "redirect:/usuario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario.");
        }
        return "redirect:/usuario/listado";
    }
}
