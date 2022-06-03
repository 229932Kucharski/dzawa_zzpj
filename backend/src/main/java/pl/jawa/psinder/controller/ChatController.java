package pl.jawa.psinder.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.service.ChatService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {


    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("")
    public List<Chat> getChats() {
        return chatService.getAllChats();
    }

    @GetMapping(value = "", params = "id")
    public List<Chat> getChatsByConnectionId(@RequestParam("id") String id) {
        return chatService.getChatByConnectionId(Long.parseLong(id));
    }
}
