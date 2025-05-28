package com.exercice.deviceapi.controller;

import com.exercice.deviceapi.model.Device;
import com.exercice.deviceapi.model.State;
import com.exercice.deviceapi.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
@Tag(name = "Device API", description = "Manage devices")
public class DeviceController {
    DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @PostMapping("/create")
    @Operation(summary = "Create new Device", description = "Create new Device")
    public ResponseEntity<Device> createDevice(@RequestBody Device deviceRequest){
        return new ResponseEntity<>(deviceService.createDevice(deviceRequest), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update Device", description = "update device by ID")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceUpdate){
        return ResponseEntity.ok(deviceService.updateDevice(id,deviceUpdate));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get device", description = "Get device by ID")
    public ResponseEntity<Device> getDeviceById(@PathVariable  Long id){
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }
    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get device", description = "Get device by Brand")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand){
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }
    @GetMapping("/state/{state}")
    @Operation(summary = "Get device",description = "Get device by State")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable State state){
        return  ResponseEntity.ok(deviceService.getDevicesByState(state));
    }
    @GetMapping("/all")
    @Operation(summary = "Get all the Devices", description = "Get all the Devices")
    public ResponseEntity<List<Device>> getDevices(){
        return  ResponseEntity.ok(deviceService.getAllDevices());
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device", description = "Delete device by ID")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id){
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

}
