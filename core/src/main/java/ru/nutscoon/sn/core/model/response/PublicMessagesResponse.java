package ru.nutscoon.sn.core.model.response;

import java.time.LocalDateTime;
import java.util.Collection;

public class PublicMessagesResponse {

    private int personId;
    private Collection<PublicMessagesResponseItem> messages;


    public PublicMessagesResponse(int personId, Collection<PublicMessagesResponseItem> messages) {
        this.personId = personId;
        this.messages = messages;
    }


    public int getPersonId() {
        return personId;
    }

    public Collection<PublicMessagesResponseItem> getMessages() {
        return messages;
    }

    public static class PublicMessagesResponseItem {
        private int id;
        private String message;
        private LocalDateTime dateTime;


        public PublicMessagesResponseItem(int id, String message, LocalDateTime dateTime) {
            this.id = id;
            this.message = message;
            this.dateTime = dateTime;
        }


        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
