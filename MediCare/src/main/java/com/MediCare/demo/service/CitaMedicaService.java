package com.MediCare.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.MediCare.demo.domain.CitaMedica;

public interface CitaMedicaService {

    List<CitaMedica> getCitas();

    Optional<CitaMedica> getCita(Long id);

    CitaMedica save(CitaMedica cita);

    void delete(Long id);

    List<CitaMedica> getCitasPorEstado(boolean activa);

    List<CitaMedica> getCitasPorRangoFechas(LocalDate desde, LocalDate hasta);

    List<CitaMedica> getCitasPorEspecialidad(String especialidad);

    long contarCitasActivas();

    List<Object[]> promedioCostoPorEspecialidad();
}
