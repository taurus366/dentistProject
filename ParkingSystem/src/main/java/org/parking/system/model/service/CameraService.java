package org.parking.system.model.service;

import org.parking.system.model.entity.Camera;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CameraService {

    private final CameraRepository repository;

    public CameraService(CameraRepository repository) {
        this.repository = repository;
    }

    public Optional<Camera> get(Long id) {
        return repository.findById(id);
    }

    public Camera update(Camera entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Collection<Camera> findAll() {
        return repository.findAll();
    }

    public Camera save(Camera entity) {
        return repository.save(entity);
    }
}
