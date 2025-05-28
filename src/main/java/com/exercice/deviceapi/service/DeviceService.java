package com.exercice.deviceapi.service;

import com.exercice.deviceapi.model.Device;
import com.exercice.deviceapi.model.State;
import com.exercice.deviceapi.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeviceService {
    DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    public Device createDevice(Device device){
        return deviceRepository.save(device);
    }
    public Device updateDevice(Long id, Device updatedDevice) {
        var existing = deviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));

        // If the device is in use, name and brand can't be updated
        if (existing.getState() == State.IN_USE) {
            if (!existing.getName().equals(updatedDevice.getName()) ||
                    !existing.getBrand().equals(updatedDevice.getBrand())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update name or brand of an in-use device");
            }
        }
        if(existing.getState() != State.IN_USE) {
            existing.setName(updatedDevice.getName());
            existing.setBrand(updatedDevice.getBrand());

        }
        existing.setState(updatedDevice.getState());
        return deviceRepository.save(existing);
    }
    public Device getDeviceById(Long id){
        return deviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    }
    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }
    public List<Device> getDevicesByBrand(String brand){
        return deviceRepository.findByBrand(brand);
    }
    public List<Device> getDevicesByState(State state){
        return deviceRepository.findByState(state);
    }
    public void deleteDeviceById(Long id){
        Device device = deviceRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Device Not Found"));
        if(device.getState() == State.IN_USE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device is IN-USE, cannot be deleted");
        }else {
            deviceRepository.deleteById(id);
        }

    }
}
