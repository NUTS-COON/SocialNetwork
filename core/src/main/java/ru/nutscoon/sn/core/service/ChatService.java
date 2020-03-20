package ru.nutscoon.sn.core.service;

import ru.nutscoon.sn.core.model.request.SendMessageModel;
import ru.nutscoon.sn.core.model.response.ChatListResponse;
import ru.nutscoon.sn.core.model.response.ChatMessagesResponse;

public interface ChatService {
    void sendMessage(SendMessageModel model, int personId);
    ChatListResponse getChats(int personId);
    ChatMessagesResponse getChatMessages(int personId, int chatId);
    int createChat(int currentPersonId, int personId);
}
