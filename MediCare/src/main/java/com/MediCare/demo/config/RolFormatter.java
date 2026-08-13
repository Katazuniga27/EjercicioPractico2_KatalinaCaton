package com.MediCare.demo.config;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import com.MediCare.demo.domain.Rol;
import com.MediCare.demo.repository.RolRepository;

@Component
public class RolFormatter implements Formatter<Rol> {

    private final RolRepository rolRepository;

    public RolFormatter(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isBlank()) {
            return null;
        }
        return rolRepository.findById(Long.parseLong(text))
                .orElseThrow(() -> new ParseException("Rol no encontrado: " + text, 0));
    }

    @Override
    public String print(Rol rol, Locale locale) {
        return (rol == null || rol.getId() == null) ? "" : rol.getId().toString();
    }
}
