package pl.jawa.psinder.mapper;

import org.springframework.stereotype.Service;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.entity.Chat;

@Service
public class ChatMapper {

    public ChatDto fromDomainModel(Chat source, String username) {
        return new ChatDto(
                source.getUser().getId(),
                username,
                source.getText()
        );
    }
}
