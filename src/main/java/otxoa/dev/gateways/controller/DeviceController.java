package otxoa.dev.gateways.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otxoa.dev.gateways.domain.Device;
import otxoa.dev.gateways.repository.DeviceRepository;
import otxoa.dev.gateways.repository.GatewayRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/gateways/{gatewayId}/devices")
public class DeviceController {

    private final GatewayRepository gatewayRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository, GatewayRepository gatewayRepository) {
        this.deviceRepository = deviceRepository;
        this.gatewayRepository = gatewayRepository;
    }

    @GetMapping
    public Page<Device> getAllGatewayDevices(@PathVariable Long gatewayId, Pageable pageable) {
        if (!gatewayRepository.existsById(gatewayId)) {
            throw new ResourceNotFoundException("GatewayId " + gatewayId + " not found");
        }
        return deviceRepository.findByGatewayId(gatewayId, pageable);
    }

    @PostMapping
    public Device createGatewayDevice(@PathVariable Long gatewayId, @RequestBody Device device) {
        return gatewayRepository.findById(gatewayId).map(gateway -> {
            device.setGateway(gateway);
            return deviceRepository.save(device);
        }).orElseThrow(() -> new ResourceNotFoundException("GatewayId " + gatewayId + " not found"));
    }

    @PatchMapping("/{deviceId}")
    public Device updateGatewayDevice(@PathVariable Long gatewayId, @PathVariable Long deviceId, @Valid @RequestBody Device patchDevice) {
        return deviceRepository.findByIdAndGatewayId(deviceId, gatewayId).map(device -> {
            Optional.ofNullable(patchDevice.getUid()).ifPresent(device::setUid);
            Optional.ofNullable(patchDevice.getVendor()).ifPresent(device::setVendor);
            Optional.ofNullable(patchDevice.getStatus()).ifPresent(device::setStatus);
            return deviceRepository.save(device);
        }).orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId + " and gateway id " + gatewayId));
    }

    @PutMapping("/{deviceId}")
    public Device replaceGatewayDevice(@PathVariable Long gatewayId, @PathVariable Long deviceId, @Valid @RequestBody Device putDevice) {
        return deviceRepository.findById(deviceId).map(device -> {
            device.setUid(putDevice.getUid());
            device.setVendor(putDevice.getVendor());
            device.setStatus(putDevice.getStatus());
            return deviceRepository.save(device);
        }).orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId + " and gateway id " + gatewayId));
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Object> deleteGatewayDevice(@PathVariable Long gatewayId, @PathVariable Long deviceId) {
        return deviceRepository.findByIdAndGatewayId(deviceId, gatewayId).map(device -> {
            deviceRepository.delete(device);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId + " and gateway id " + gatewayId));
    }
}
