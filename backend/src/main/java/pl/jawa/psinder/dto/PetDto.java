package pl.jawa.psinder.dto;

import lombok.Getter;
import pl.jawa.psinder.entity.PetAddress;

@Getter
public class PetDto {

    private String name;
    private String race;
    private String size;
    private String description;
    private PetAddress address;

}
