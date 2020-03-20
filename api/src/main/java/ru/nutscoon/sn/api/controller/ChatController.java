package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.model.request.SendMessageModel;
import ru.nutscoon.sn.core.model.response.BaseResponse;
import ru.nutscoon.sn.core.model.response.ChatListResponse;
import ru.nutscoon.sn.core.model.response.ChatMessagesResponse;
import ru.nutscoon.sn.core.model.response.CreateChatResponse;
import ru.nutscoon.sn.core.service.ChatService;
import ru.nutscoon.sn.api.service.PersonAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    private final ChatService chatService;
    private final PersonAuthService personAuthService;


    @Autowired
    public ChatController(ChatService chatService, PersonAuthService personAuthService) {
        this.chatService = chatService;
        this.personAuthService = personAuthService;
    }


    @GetMapping("createChat/{personId}")
    public CreateChatResponse createChat(@PathVariable("personId") int personId){
        int chatId = chatService.createChat(personAuthService.getCurrentPersonId(), personId);
        return new CreateChatResponse(chatId);
    }

    @PostMapping("sendMessage")
    public BaseResponse sendMessage(@Valid @RequestBody SendMessageModel model) {
        chatService.sendMessage(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @GetMapping("chats")
    public ChatListResponse chats() {
        return chatService.getChats(personAuthService.getCurrentPersonId());
    }

    @GetMapping("chats/{chatId}")
    public ChatMessagesResponse chats(@PathVariable("chatId") int chatId) {
        return chatService.getChatMessages(personAuthService.getCurrentPersonId(), chatId);
    }
}
