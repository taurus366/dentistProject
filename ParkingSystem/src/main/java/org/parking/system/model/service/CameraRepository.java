package org.parking.system.model.service;

import org.parking.system.model.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
}
