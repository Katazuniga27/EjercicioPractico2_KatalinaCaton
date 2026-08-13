package com.MediCare.demo.serviceimpl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.MediCare.demo.domain.Rol;
import com.MediCare.demo.repository.RolRepository;
import com.MediCare.demo.service.RolService;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> getRol(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }
}
