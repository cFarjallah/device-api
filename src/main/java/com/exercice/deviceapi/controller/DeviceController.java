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

/**
 * REST controller for managing Device resources.
 */
@RestController
@RequestMapping("api/devices")
@Tag(name = "Device API", description = "Manage devices")
public class DeviceController {
    DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Create a new device.
     *
     * @param deviceRequest the device details
     * @return the created device with HTTP 201 status
     */
    @PostMapping("/create")
    @Operation(summary = "Create new Device", description = "Create new Device")
    public ResponseEntity<Device> createDevice(@RequestBody Device deviceRequest) {
        return new ResponseEntity<>(deviceService.createDevice(deviceRequest), HttpStatus.CREATED);
    }

    /**
     * Update an existing device by ID.
     *
     * @param id           the ID of the device to update
     * @param deviceUpdate the new device data
     * @return the updated device
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Device", description = "update device by ID")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceUpdate) {
        return ResponseEntity.ok(deviceService.updateDevice(id, deviceUpdate));
    }

    /**
     * Get a device by its ID.
     *
     * @param id the ID of the device
     * @return the found device or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get device", description = "Get device by ID")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    /**
     * Get devices filtered by brand.
     *
     * @param brand the brand to filter by
     * @return a list of devices with the specified brand
     */
    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get device", description = "Get device by Brand")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }

    /**
     * Get devices filtered by state.
     *
     * @param state the brand to filter by
     * @return a list of devices with the specified state
     */
    @GetMapping("/state/{state}")
    @Operation(summary = "Get device", description = "Get device by State")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable State state) {
        return ResponseEntity.ok(deviceService.getDevicesByState(state));
    }

    /**
     * Get all devices.
     *
     * @return a list of all devices
     */
    @GetMapping("/all")
    @Operation(summary = "Get all the Devices", description = "Get all the Devices")
    public ResponseEntity<List<Device>> getDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    /**
     * Delete a device by ID.
     *
     * @param id the ID of the device to delete
     * @return HTTP 204 No Content on success
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device", description = "Delete device by ID")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

}
