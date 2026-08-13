package com.MediCare.demo.service;

import java.util.List;
import java.util.Optional;
import com.MediCare.demo.domain.Usuario;

public interface UsuarioService {

    List<Usuario> getUsuarios();

    Optional<Usuario> getUsuario(Long id);

    Usuario save(Usuario usuario);

    void delete(Long id);

    List<Usuario> getUsuariosPorRol(String nombreRol);
}
