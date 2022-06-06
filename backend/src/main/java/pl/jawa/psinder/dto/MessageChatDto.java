package pl.jawa.psinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageChatDto {

    private long user_Id;
    private String username;
    private String text;
}
