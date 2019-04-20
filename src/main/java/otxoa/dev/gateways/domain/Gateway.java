package otxoa.dev.gateways.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Gateway {

    private static final String IPV4_ADDRESS_PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String serialNumber;

    @NotBlank
    private String name;

    @Pattern(regexp = IPV4_ADDRESS_PATTERN, message = "IP Address should have IPv4 format e.g. '192.168.1.1'")
    private String ipv4Address;
}
