package com.MediCare.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.MediCare.demo.domain.CitaMedica;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    // 1. Consulta derivada: citas por estado (activas / inactivas)
    List<CitaMedica> findByActiva(boolean activa);

    // 2. Consulta derivada: citas dentro de un rango de fechas
    List<CitaMedica> findByFechaBetween(LocalDate desde, LocalDate hasta);

    // 3. Consulta derivada: citas por coincidencia parcial de especialidad
    List<CitaMedica> findByEspecialidadContainingIgnoreCase(String especialidad);

    // 4. Consulta personalizada (@Query): total de citas activas
    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.activa = true")
    long contarCitasActivas();

    // 5. Consulta personalizada (@Query): costo promedio de citas activas por especialidad
    @Query("SELECT c.especialidad, AVG(c.costo) FROM CitaMedica c WHERE c.activa = true GROUP BY c.especialidad")
    List<Object[]> promedioCostoPorEspecialidad();
}
