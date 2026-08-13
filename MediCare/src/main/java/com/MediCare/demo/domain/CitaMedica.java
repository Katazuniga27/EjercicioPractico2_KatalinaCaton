package com.MediCare.demo.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "cita_medica")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "paciente_nombre", length = 150)
    private String pacienteNombre;

    @NotBlank
    @Column(length = 100)
    private String especialidad;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    private Double costo;

    @Column(nullable = false)
    private boolean activa = true;
}
