package com.MediCare.demo.service;

import java.util.List;
import java.util.Optional;
import com.MediCare.demo.domain.Rol;

public interface RolService {

    List<Rol> getRoles();

    Optional<Rol> getRol(Long id);

    Rol save(Rol rol);

    void delete(Long id);
}
