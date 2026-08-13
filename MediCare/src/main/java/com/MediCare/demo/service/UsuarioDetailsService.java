package com.MediCare.demo.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.MediCare.demo.domain.Usuario;
import com.MediCare.demo.repository.UsuarioRepository;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        if (!usuario.isActivo()) {
            throw new UsernameNotFoundException("El usuario se encuentra inactivo: " + email);
        }

        String rolSeguridad = "ROLE_" + usuario.getRol().getNombre().toUpperCase();

        return new User(usuario.getEmail(), usuario.getPassword(),
                Collections.singleton(() -> rolSeguridad));
    }
}
