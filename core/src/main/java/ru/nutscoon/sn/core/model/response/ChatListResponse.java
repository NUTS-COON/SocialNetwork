package ru.nutscoon.sn.core.model.response;

import java.util.Collection;

public class ChatListResponse {
    private int totalCount;
    private Collection<ChatListItem> chatListItems;


    public ChatListResponse(int totalCount, Collection<ChatListItem> chatListItems) {
        this.totalCount = totalCount;
        this.chatListItems = chatListItems;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public Collection<ChatListItem> getChatListItems() {
        return chatListItems;
    }


    public static class ChatListItem{
        private int chatId;
        private int personId;
        private String personName;

        public ChatListItem(int chatId, int personId, String personName) {
            this.chatId = chatId;
            this.personId = personId;
            this.personName = personName;
        }

        public int getChatId() {
            return chatId;
        }

        public int getPersonId() {
            return personId;
        }

        public String getPersonName() {
            return personName;
        }
    }
}
