package denis.f108349.hospital.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class BaseEntity {
    @Id
    protected String id;
}
