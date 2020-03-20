package ru.nutscoon.sn.core.model.response;

import java.time.LocalDateTime;
import java.util.Collection;

public class PublicInfoResponse {
    private String name;
    private String description;
    private PublicSubscriber owner;
    private Collection<PublicSubscriber> admins;
    private LocalDateTime created;


    public PublicInfoResponse(
            String name,
            String description,
            PublicSubscriber owner,
            Collection<PublicSubscriber> admins,
            LocalDateTime created) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.admins = admins;
        this.created = created;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public PublicSubscriber getOwner() {
        return owner;
    }

    public Collection<PublicSubscriber> getAdmins() {
        return admins;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
