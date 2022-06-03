package pl.jawa.psinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDto {

    private long user_Id;
    private String username;
    private String text;
}
