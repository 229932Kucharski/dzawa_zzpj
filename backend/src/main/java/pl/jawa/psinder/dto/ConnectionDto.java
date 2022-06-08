package pl.jawa.psinder.dto;

import lombok.Getter;
import lombok.Setter;
import pl.jawa.psinder.enums.Status;

@Getter
@Setter
public class ConnectionDto {
    private long id;
    private long pet_id;
    private long owner_id;
    private long walker_id;
    private Status status;
}
