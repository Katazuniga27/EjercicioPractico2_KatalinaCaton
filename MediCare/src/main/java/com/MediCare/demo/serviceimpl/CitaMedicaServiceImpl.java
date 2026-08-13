package com.MediCare.demo.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.MediCare.demo.domain.CitaMedica;
import com.MediCare.demo.repository.CitaMedicaRepository;
import com.MediCare.demo.service.CitaMedicaService;

@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaServiceImpl(CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
    }

    @Override
    public List<CitaMedica> getCitas() {
        return citaMedicaRepository.findAll();
    }

    @Override
    public Optional<CitaMedica> getCita(Long id) {
        return citaMedicaRepository.findById(id);
    }

    @Override
    public CitaMedica save(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }

    @Override
    public void delete(Long id) {
        citaMedicaRepository.deleteById(id);
    }

    @Override
    public List<CitaMedica> getCitasPorEstado(boolean activa) {
        return citaMedicaRepository.findByActiva(activa);
    }

    @Override
    public List<CitaMedica> getCitasPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return citaMedicaRepository.findByFechaBetween(desde, hasta);
    }

    @Override
    public List<CitaMedica> getCitasPorEspecialidad(String especialidad) {
        return citaMedicaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    @Override
    public long contarCitasActivas() {
        return citaMedicaRepository.contarCitasActivas();
    }

    @Override
    public List<Object[]> promedioCostoPorEspecialidad() {
        return citaMedicaRepository.promedioCostoPorEspecialidad();
    }
}
