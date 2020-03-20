package ru.nutscoon.sn.core.service;

import ru.nutscoon.sn.core.model.request.GetPostsModel;
import ru.nutscoon.sn.core.model.request.SendPostModel;
import ru.nutscoon.sn.core.model.response.GetPostsResponse;

public interface PublicPostService {
    int sendPost(SendPostModel model, int personId);
    void publishPost(int currentPersonId, int postId);
    GetPostsResponse getPosts(GetPostsModel model, int personId);
}
