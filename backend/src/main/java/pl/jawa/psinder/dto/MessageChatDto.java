package pl.jawa.psinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MessageChatDto {

    private String username;
    private String text;
    private Date date;
}
