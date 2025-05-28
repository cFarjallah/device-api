
package com.exercice.deviceapi.service;


import com.exercice.deviceapi.model.Device;
import com.exercice.deviceapi.model.State;
import com.exercice.deviceapi.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DeviceService using JUnit and Mockito.
 */
public class DeviceServiceTest {
    private DeviceService deviceService;
    private DeviceRepository deviceRepository;

    /**
     * Set up test dependencies before each test.
     * This method is annotated with @BeforeEach so it's run before every test method.
     */
    @BeforeEach
    void setUp() {
        deviceRepository = Mockito.mock(DeviceRepository.class);
        deviceService = new DeviceService(deviceRepository);
    }

    /**
     * Test creating a new device.
     */
    @Test
    void testCreateDevice() {
        var device = new Device("iPhone", "Apple", State.AVAILABLE);
        Mockito.when(deviceRepository.save(device)).thenReturn(device);
        var result = deviceService.createDevice(device);
        assertEquals("iPhone", result.getName());
        verify(deviceRepository).save(device);
    }

    /**
     * Test retrieving a device by ID when it exists.
     */
    @Test
    void testGetDeviceByIdSucces() {
        var device = new Device("Pixel", "Google", State.AVAILABLE);
        device.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        var result = deviceService.getDeviceById(1L);
        assertEquals("Pixel", result.getName());
    }

    /**
     * Test retrieving a device by ID when it doesn't exist.
     * This should throw a ResponseStatusException (404).
     */
    @Test
    void testGetDeviceByIdNotFound() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> deviceService.getDeviceById(1L));
    }

    /**
     * Test updating a device when it's not in use.
     */
    @Test
    void testUpdateDeviceWhenAvailable() {
        var original = new Device("Pixel", "Google", State.AVAILABLE);
        original.setId(1L);
        var updated = new Device("Pixel 8", "Google", State.IN_USE);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(original));
        when(deviceRepository.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var result = deviceService.updateDevice(1L, updated);
        assertEquals("Pixel 8", result.getName());
        assertEquals(State.IN_USE, result.getState());
    }

    /**
     * Test updating name or brand of a device that's IN_USE — should fail.
     */
    @Test
    void testUpdateDeviceInUseNameChangeThrows() {
        var original = new Device("Pixel", "Google", State.IN_USE);
        original.setId(1L);
        var updated = new Device("Pixel 7", "Google", State.IN_USE);  // name changed

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(original));

        assertThrows(ResponseStatusException.class, () -> deviceService.updateDevice(1L, updated));
    }

    /**
     * Test deleting a device that is AVAILABLE — should succeed.
     */
    @Test
    void testDeleteDeviceSuccess() {
        var device = new Device("iPad", "Apple", State.AVAILABLE);
        device.setId(2L);
        when(deviceRepository.findById(2L)).thenReturn(Optional.of(device));

        deviceService.deleteDeviceById(2L);

        verify(deviceRepository).deleteById(2L);
    }

    /**
     * Test deleting a device that is IN_USE — should throw error.
     */
    @Test
    void testDeleteDeviceInUseThrows() {
        var device = new Device("iPad", "Apple", State.IN_USE);
        device.setId(3L);
        when(deviceRepository.findById(3L)).thenReturn(Optional.of(device));

        assertThrows(ResponseStatusException.class, () -> deviceService.deleteDeviceById(3L));
    }
}