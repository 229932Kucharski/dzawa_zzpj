package pl.jawa.psinder.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public List<Chat> getChatByConnectionId(long id) {
        return chatRepository.findChatsByConnectionId(id);
    }

    public Chat getChatById(long id) {
        return chatRepository.findById(id);
    }

    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public void deleteChat(long id) {
        chatRepository.deleteById(id);
    }
}
