package otxoa.dev.gateways.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import otxoa.dev.gateways.domain.Device;

import java.util.Optional;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByGatewayId(Long gatewayId, Pageable pageable);

    Optional<Device> findByIdAndGatewayId(Long id, Long gatewayId);
}
