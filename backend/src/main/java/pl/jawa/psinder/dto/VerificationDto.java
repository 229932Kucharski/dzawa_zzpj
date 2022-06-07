package pl.jawa.psinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationDto {
    private long regon;
    private String address;
}
