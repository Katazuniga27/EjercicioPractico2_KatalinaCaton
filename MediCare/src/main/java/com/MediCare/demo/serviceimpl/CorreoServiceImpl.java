package com.MediCare.demo.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.MediCare.demo.service.CorreoService;

@Service
public class CorreoServiceImpl implements CorreoService {

    private static final Logger log = LoggerFactory.getLogger(CorreoServiceImpl.class);

    private final JavaMailSender mailSender;

    public CorreoServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreoBienvenida(String para, String nombre) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(para);
            mensaje.setSubject("Bienvenido(a) a MediCare");
            mensaje.setText("Hola " + nombre + ",\n\n"
                    + "Tu cuenta en la plataforma MediCare fue creada exitosamente.\n"
                    + "Ya puedes iniciar sesion con tu correo (" + para + ") y la contrasena "
                    + "que se te asigno.\n\n"
                    + "Saludos,\nEquipo MediCare");
            mailSender.send(mensaje);
        } catch (Exception e) {
            // No se detiene la creacion del usuario si el correo falla,
            // solo se deja un registro en el log (por ejemplo, credenciales de prueba)
            log.warn("No se pudo enviar el correo de bienvenida a {}: {}", para, e.getMessage());
        }
    }
}
