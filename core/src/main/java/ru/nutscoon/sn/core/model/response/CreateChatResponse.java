package ru.nutscoon.sn.core.model.response;

public class CreateChatResponse extends BaseResponse {
    private int chatId;

    public CreateChatResponse(int chatId) {
        super(true);
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }
}
