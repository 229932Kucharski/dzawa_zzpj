package pl.jawa.psinder.service;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.repository.ChatRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class ChatService {

    private ChatRepository chatRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public List<Chat> getChatsByUserId(long id) {
        return chatRepository.findChatsById(id);
    }

    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat updateChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
