package otxoa.dev.gateways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otxoa.dev.gateways.domain.Gateway;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
}
