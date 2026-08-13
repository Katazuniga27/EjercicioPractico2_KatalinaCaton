package com.MediCare.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.MediCare.demo.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    // Consulta derivada: buscar usuarios por el nombre del rol asignado
    List<Usuario> findByRol_NombreIgnoreCase(String nombreRol);

    // Consulta personalizada (@Query): contar cuantos usuarios activos hay por rol
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.nombre = :nombreRol AND u.activo = true")
    long contarUsuariosActivosPorRol(@Param("nombreRol") String nombreRol);
}
