package com.exercice.deviceapi.service;

import com.exercice.deviceapi.model.Device;
import com.exercice.deviceapi.model.State;
import com.exercice.deviceapi.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service class that handles business logic for device management.
 */
@Service
public class DeviceService {
    DeviceRepository deviceRepository;

    /**
     * Constructor-based dependency injection for DeviceRepository.
     *
     * @param deviceRepository the repository used to manage Device persistence
     */
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    /**
     * Updates an existing device by ID.
     * - If the device is IN_USE, name and brand cannot be changed.
     * - State can always be updated.
     *
     * @param id            the ID of the device to update
     * @param updatedDevice the updated device data
     * @return the saved updated device
     * @throws ResponseStatusException if the device is not found or validation fails
     */
    public Device updateDevice(Long id, Device updatedDevice) {
        var existing = deviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
        if (existing.getState() == State.IN_USE) {
            if (!existing.getName().equals(updatedDevice.getName()) ||
                    !existing.getBrand().equals(updatedDevice.getBrand())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update name or brand of an in-use device");
            }
        }
        if (existing.getState() != State.IN_USE) {
            existing.setName(updatedDevice.getName());
            existing.setBrand(updatedDevice.getBrand());

        }
        existing.setState(updatedDevice.getState());
        return deviceRepository.save(existing);
    }

    /**
     * Retrieves a device by its ID.
     *
     * @param id the ID of the device to retrieve
     * @return the found device
     * @throws ResponseStatusException if the device is not found
     */
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    }

    /**
     * Retrieves all devices from the database.
     *
     * @return list of all devices
     */
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Retrieves all devices matching the brand.
     *
     * @param brand the brand to filter devices by
     * @return a list of devices with the brand
     */
    public List<Device> getDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    /**
     * Retrieves all devices matching the state.
     *
     * @param state the state to filter devices by
     * @return a list of devices with the state
     */
    public List<Device> getDevicesByState(State state) {
        return deviceRepository.findByState(state);
    }

    /**
     * Deletes a device by ID.
     * - Devices in IN_USE state cannot be deleted
     *
     * @param id the ID of the device to delete
     * @throws ResponseStatusException if the device is not found or is IN_USE
     */
    public void deleteDeviceById(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device Not Found"));
        if (device.getState() == State.IN_USE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device is IN-USE, cannot be deleted");
        } else {
            deviceRepository.deleteById(id);
        }

    }
}
