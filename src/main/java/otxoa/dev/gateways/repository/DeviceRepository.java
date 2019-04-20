package otxoa.dev.gateways.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import otxoa.dev.gateways.domain.Device;


public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {
    Page<Device> findByGatewayId(Long gatewayId, Pageable pageable);
}
