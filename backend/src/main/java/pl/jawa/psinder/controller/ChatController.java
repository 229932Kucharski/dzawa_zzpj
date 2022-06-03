package pl.jawa.psinder.controller;


import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.entity.User;
import pl.jawa.psinder.mapper.ChatMapper;
import pl.jawa.psinder.service.ChatService;
import pl.jawa.psinder.service.UserService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final ChatMapper chatMapper;

    public ChatController(ChatService chatService, UserService userService, ChatMapper chatMapper) {
        this.chatService = chatService;
        this.userService = userService;
        this.chatMapper = chatMapper;
    }

    @GetMapping("")
    public List<Chat> getChats() {
        return chatService.getAllChats();
    }

    @GetMapping(value = "", params = "id")
    public List<ChatDto> getChatByConnectionId(@RequestParam("id") String id) {
        List<Chat> chats = chatService.getChatByConnectionId(Long.parseLong(id));
        List<ChatDto> chatsDto = new ArrayList<>();
        for(Chat c: chats) {
            if(userService.getUserById(c.getUser().getId()).isPresent()) {
                chatsDto.add(
                        chatMapper.fromDomainModel(
                                c,
                                userService.getUserById(c.getUser().getId()).get().getUsername()
                        )
                );
            }
        }
        return chatsDto;
    }

//    @PostMapping()
//    public Chat createChat() {
//
//    }
//
//    @PutMapping()
//    public Chat saveChat() {
//
//    }
}
