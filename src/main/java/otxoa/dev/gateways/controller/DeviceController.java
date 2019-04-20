package otxoa.dev.gateways.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import otxoa.dev.gateways.domain.Device;
import otxoa.dev.gateways.repository.DeviceRepository;
import otxoa.dev.gateways.repository.GatewayRepository;

@RestController
@RequestMapping(value = "/gateways/{gateway_id}/devices")
public class DeviceController {

    private final GatewayRepository gatewayRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository, GatewayRepository gatewayRepository) {
        this.deviceRepository = deviceRepository;
        this.gatewayRepository = gatewayRepository;
    }

    @GetMapping
    public Page<Device> getAllGatewayDevices(@PathVariable("gateway_id") Long gatewayId, Pageable pageable) {
        return deviceRepository.findByGatewayId(gatewayId, pageable);
    }

    @PostMapping
    public Device createGatewayDevice(@PathVariable("gateway_id") Long gatewayId, @RequestBody Device device) {
        return gatewayRepository.findById(gatewayId).map(gateway -> {
            device.setGateway(gateway);
            return deviceRepository.save(device);
        }).orElseThrow(() -> new ResourceNotFoundException("GatewayId " + gatewayId + " not found"));
    }
}
