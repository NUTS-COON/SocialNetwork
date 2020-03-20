package ru.nutscoon.sn.core.model.response;

import java.time.LocalDateTime;
import java.util.Collection;

public class GetPostsResponse {
    private int count;
    private Collection<PostItem> posts;


    public GetPostsResponse(int count, Collection<PostItem> posts) {
        this.count = count;
        this.posts = posts;
    }


    public int getCount() {
        return count;
    }

    public Collection<PostItem> getPosts() {
        return posts;
    }


    public static class PostItem {
        private int id;
        private String author;
        private int authorId;
        private String text;
        private LocalDateTime publishDate;
        private Boolean isPublished;
        private LocalDateTime created;


        public PostItem(int id, String author, int authorId, String text, LocalDateTime publishDate) {
            this.id = id;
            this.author = author;
            this.authorId = authorId;
            this.text = text;
            this.publishDate = publishDate;
        }

        public int getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getText() {
            return text;
        }

        public LocalDateTime getPublishDate() {
            return publishDate;
        }

        public Boolean getPublished() {
            return isPublished;
        }

        public void setPublished(Boolean published) {
            isPublished = published;
        }

        public LocalDateTime getCreated() {
            return created;
        }

        public void setCreated(LocalDateTime created) {
            this.created = created;
        }
    }
}
