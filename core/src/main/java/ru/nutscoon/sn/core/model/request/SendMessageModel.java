package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SendMessageModel {

    @NotNull(message = "{sendMessage.required.chatId}")
    private Integer chatId;
    @NotEmpty(message = "{sendMessage.required.message}")
    private String message;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
