package pl.jawa.psinder.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.ChatDto;
import pl.jawa.psinder.dto.MessageChatDto;
import pl.jawa.psinder.entity.Chat;
import pl.jawa.psinder.mapper.ChatMapper;
import pl.jawa.psinder.repository.ConnectionRepository;
import pl.jawa.psinder.service.ChatService;
import pl.jawa.psinder.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final ChatMapper chatMapper;
    private final ConnectionRepository connectionRepository;

    public ChatController(ChatService chatService, UserService userService, ChatMapper chatMapper, ConnectionRepository connectionRepository) {
        this.chatService = chatService;
        this.userService = userService;
        this.chatMapper = chatMapper;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/all")
    public List<Chat> getChats() {
        return chatService.getAllChats();
    }

    @GetMapping("")
    public List<MessageChatDto> getChatsWithLastMessage() {
        List<List<Chat>> chats = chatService.getAllChats().stream().collect(Collectors.groupingBy(
                p -> p.getConnection().getId()
        )).values().stream().toList();
        List<MessageChatDto> chatDtos = new ArrayList<>();
        Chat c;
        for(List<Chat> chatList: chats) {
            c = chatList.get(chatList.size() - 1);
            chatDtos.add(
                    chatMapper.fromDomainModel(
                            c,
                            userService.getUserById(c.getUser().getId()).get().getUsername()
                    )
            );
        }
        return chatDtos;
    }

    @GetMapping(value = "", params = "id")
    public List<MessageChatDto> getChatByConnectionId(@RequestParam("id") String id) {
        List<Chat> chats = chatService.getChatByConnectionId(Long.parseLong(id));
        List<MessageChatDto> chatsDto = new ArrayList<>();
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

    @PostMapping(
            value = "",
            params = "id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Chat> createCMessage(@RequestParam("id") String id, ChatDto chatDto) {
        final Chat chat = chatService.addChat(chatMapper.toDomailModel(chatDto, Long.parseLong(id)));
        return ResponseEntity.ok(chat);
    }

//    @PatchMapping(value = "",
//            params = "id",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Chat>> saveChat(@RequestParam("id") String id, List<ChatDto> chatDtos) {
//
//        for(ChatDto chatDto: chatDtos) {
//
//        }
//        return ResponseEntity.ok()
//    }
}
