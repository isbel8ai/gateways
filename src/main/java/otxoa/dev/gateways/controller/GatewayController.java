package otxoa.dev.gateways.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otxoa.dev.gateways.domain.Gateway;
import otxoa.dev.gateways.repository.GatewayRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/gateways")
public class GatewayController {

    private final GatewayRepository gatewayRepository;

    @Autowired
    public GatewayController(GatewayRepository gatewayRepository) {
        this.gatewayRepository = gatewayRepository;
    }

    @GetMapping
    public Page<Gateway> getAllGateways(Pageable pageable) {
        return gatewayRepository.findAll(pageable);
    }

    @PostMapping
    public Gateway createGateway(@Valid @RequestBody Gateway gateway) {
        return gatewayRepository.save(gateway);
    }

    @PatchMapping("/{gatewayId}")
    public Gateway updateGateway(@PathVariable Long gatewayId, @Valid @RequestBody Gateway patchGateway) {
        return gatewayRepository.findById(gatewayId).map(gateway -> {
            Optional.ofNullable(patchGateway.getSerialNumber()).ifPresent(gateway::setSerialNumber);
            Optional.ofNullable(patchGateway.getName()).ifPresent(gateway::setName);
            Optional.ofNullable(patchGateway.getIpv4Address()).ifPresent(gateway::setIpv4Address);
            return gatewayRepository.save(gateway);
        }).orElseThrow(() -> new ResourceNotFoundException("Gateway id " + gatewayId + " not found"));
    }

    @PutMapping("/{gatewayId}")
    public Gateway replaceGateway(@PathVariable Long gatewayId, @Valid @RequestBody Gateway putGateway) {
        return gatewayRepository.findById(gatewayId).map(gateway -> {
            gateway.setSerialNumber(putGateway.getSerialNumber());
            gateway.setName(putGateway.getName());
            gateway.setIpv4Address(putGateway.getIpv4Address());
            return gatewayRepository.save(gateway);
        }).orElseThrow(() -> new ResourceNotFoundException("Gateway id " + gatewayId + " not found"));
    }

    @DeleteMapping("/{gatewayId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long gatewayId) {
        return gatewayRepository.findById(gatewayId).map(gateway -> {
            gatewayRepository.delete(gateway);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Gateway id " + gatewayId + " not found"));
    }
}
