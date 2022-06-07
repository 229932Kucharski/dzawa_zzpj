package pl.jawa.psinder.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatDto {

    private long conn_Id;
    private long user_Id;
    private String text;
}
