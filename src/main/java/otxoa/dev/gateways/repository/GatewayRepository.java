package otxoa.dev.gateways.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import otxoa.dev.gateways.domain.Gateway;

public interface GatewayRepository extends PagingAndSortingRepository<Gateway, Long> {
}
