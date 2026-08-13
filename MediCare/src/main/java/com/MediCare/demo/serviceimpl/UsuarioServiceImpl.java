package com.MediCare.demo.serviceimpl;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.MediCare.demo.domain.Usuario;
import com.MediCare.demo.repository.UsuarioRepository;
import com.MediCare.demo.service.CorreoService;
import com.MediCare.demo.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CorreoService correoService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            CorreoService correoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.correoService = correoService;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        boolean esNuevo = (usuario.getId() == null);

        if (esNuevo) {
            // Usuario nuevo: se cifra la contrasena con BCrypt
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Si se esta editando y no se escribio una nueva contrasena,
            // se conserva la que ya estaba guardada
            Usuario existente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(existente != null ? existente.getPassword() : "");
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            if (existente != null) {
                usuario.setFechaCreacion(existente.getFechaCreacion());
            }
        }

        Usuario guardado = usuarioRepository.save(usuario);

        if (esNuevo) {
            // Se envia el correo de bienvenida usando Spring Mail
            correoService.enviarCorreoBienvenida(guardado.getEmail(), guardado.getNombre());
        }

        return guardado;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRol_NombreIgnoreCase(nombreRol);
    }
}
