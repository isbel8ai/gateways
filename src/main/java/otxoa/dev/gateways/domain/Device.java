package otxoa.dev.gateways.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"created", "gateway"})
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long uid;

    @NotBlank
    private String vendor;

    @CreationTimestamp
    private Date created;

    @Pattern(regexp = "^online|offline$", message = "Status value should be 'online' or 'offline'")
    private String status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Gateway gateway;
}
