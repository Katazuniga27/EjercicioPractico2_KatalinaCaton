package com.MediCare.demo.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.MediCare.demo.service.CitaMedicaService;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final CitaMedicaService citaMedicaService;

    public ConsultaController(CitaMedicaService citaMedicaService) {
        this.citaMedicaService = citaMedicaService;
    }

    @GetMapping("/listado")
    public String listado(
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) String especialidad,
            Model model) {

        model.addAttribute("totalCitasActivas", citaMedicaService.contarCitasActivas());
        model.addAttribute("promedioPorEspecialidad", citaMedicaService.promedioCostoPorEspecialidad());

        // Consulta 1: por estado (activas/inactivas)
        if (estado != null) {
            model.addAttribute("resultadoEstado", citaMedicaService.getCitasPorEstado(estado));
        } else {
            model.addAttribute("resultadoEstado", Collections.emptyList());
        }

        // Consulta 2: por rango de fechas
        if (desde != null && hasta != null) {
            model.addAttribute("resultadoFechas", citaMedicaService.getCitasPorRangoFechas(desde, hasta));
        } else {
            model.addAttribute("resultadoFechas", Collections.emptyList());
        }

        // Consulta 3: por especialidad (coincidencia parcial)
        if (especialidad != null && !especialidad.isBlank()) {
            model.addAttribute("resultadoEspecialidad",
                    citaMedicaService.getCitasPorEspecialidad(especialidad));
        } else {
            model.addAttribute("resultadoEspecialidad", Collections.emptyList());
        }

        model.addAttribute("estadoSeleccionado", estado);
        model.addAttribute("desdeSeleccionado", desde);
        model.addAttribute("hastaSeleccionado", hasta);
        model.addAttribute("especialidadSeleccionada", especialidad);

        return "consultas/listado";
    }
}
