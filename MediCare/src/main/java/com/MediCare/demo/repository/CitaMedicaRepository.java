package com.MediCare.demo.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.MediCare.demo.domain.CitaMedica;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    List<CitaMedica> findByActiva(boolean activa);

    List<CitaMedica> findByFechaBetween(LocalDate desde, LocalDate hasta);

    List<CitaMedica> findByEspecialidadContainingIgnoreCase(String especialidad);
    
    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.activa = true")
    long contarCitasActivas();
    
    @Query("SELECT c.especialidad, AVG(c.costo) FROM CitaMedica c WHERE c.activa = true GROUP BY c.especialidad")
    List<Object[]> promedioCostoPorEspecialidad();
}
