package ru.nutscoon.sn.core.model.response;

import java.time.LocalDateTime;
import java.util.Collection;

public class ChatMessagesResponse {
    private int messagesCount;
    private int participantId;
    private String name;
    Collection<ChatMessageItem> messages;


    public ChatMessagesResponse(int messagesCount, int participantId, String name, Collection<ChatMessageItem> messages) {
        this.messagesCount = messagesCount;
        this.participantId = participantId;
        this.name = name;
        this.messages = messages;
    }

    public int getMessagesCount() {
        return messagesCount;
    }

    public int getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public Collection<ChatMessageItem> getMessages() {
        return messages;
    }

    public static class ChatMessageItem {
        private int senderId;
        private String text;
        private LocalDateTime dateTime;

        public ChatMessageItem(int senderId, String text, LocalDateTime dateTime) {
            this.senderId = senderId;
            this.text = text;
            this.dateTime = dateTime;
        }

        public int getSenderId() {
            return senderId;
        }

        public String getText() {
            return text;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
