package pl.jawa.psinder.mapper;

import org.springframework.stereotype.Service;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.dto.MessageChatDto;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.repository.ConnectionRepository;
import pl.jawa.psinder.service.UserService;

@Service
public class ChatMapper {

    private final UserService userService;
    private final ConnectionRepository connectionRepository;

    public ChatMapper(UserService userService, ConnectionRepository connectionRepository) {
        this.userService = userService;
        this.connectionRepository = connectionRepository;
    }

    public MessageChatDto fromDomainModel(Chat source, String username) {
        return new MessageChatDto(
                source.getUser().getId(),
                username,
                source.getText()
        );
    }

    public Chat toDomailModel(ChatDto source, long id) {
        return new Chat(
                source.getId(),
                connectionRepository.findById(id).get(),
                userService.getUserById(source.getUser_Id()).get(),
                source.getText()
        );
    }
}
