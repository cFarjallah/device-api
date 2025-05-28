package com.exercice.deviceapi.repository;

import com.exercice.deviceapi.model.Device;
import com.exercice.deviceapi.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);

    List<Device> findByState(State state);


}
