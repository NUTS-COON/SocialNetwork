package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.AccessDeniedException;
import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelation;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelationType;
import ru.nutscoon.sn.core.model.entity.PublicPost;
import ru.nutscoon.sn.core.model.request.GetPostsModel;
import ru.nutscoon.sn.core.model.request.SendPostModel;
import ru.nutscoon.sn.core.model.response.GetPostsResponse;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonToPublicRelationRepository;
import ru.nutscoon.sn.core.repository.PublicPostRepository;
import ru.nutscoon.sn.core.service.PublicPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class PublicPostServiceImpl extends BaseService implements PublicPostService {

    private final PersonToPublicRelationRepository personToPublicRelationRepository;
    private final PublicPostRepository postRepository;

    public PublicPostServiceImpl(
            PersonRepository personRepository,
            PersonToPublicRelationRepository personToPublicRelationRepository,
            PublicPostRepository postRepository) {
        super(personRepository);
        this.personToPublicRelationRepository = personToPublicRelationRepository;
        this.postRepository = postRepository;
    }

    @Override
    public int sendPost(SendPostModel model, int personId) {
        Person person = getPerson(personId);
        PersonToPublicRelation relation = personToPublicRelationRepository.getByPublicIdAndPersonId(model.getPublicId(), person.getId());
        if (relation == null) {
            throw new AccessDeniedException();
        }

        PublicPost post = new PublicPost();
        post.setAuthor(person);
        post.setText(model.getText());
        post.setPublicId(model.getPublicId());
        post.setCreated(LocalDateTime.now());
        post.setPublished(false);

        if (relation.getRelation() == PersonToPublicRelationType.ADMIN ||
                relation.getRelation() == PersonToPublicRelationType.OWNER) {
            post.setPublished(true);
            post.setPublished(LocalDateTime.now());
        }

        return postRepository.save(post).getId();
    }

    @Override
    public void publishPost(int currentPersonId, int postId) {
        if (!personRepository.existsById(currentPersonId)) {
            throw new NotFoundException("Person not found");
        }

        PublicPost post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        PersonToPublicRelation relation = personToPublicRelationRepository.getByPublicIdAndPersonId(post.getPublicId(), currentPersonId);

        if (relation == null || relation.getRelation() == PersonToPublicRelationType.SUBSCRIBER) {
            throw new AccessDeniedException();
        }

        if (post.isPublished()) {
            throw new InvalidOperationException("Post already published");
        }

        post.setPublished(true);
        post.setPublished(LocalDateTime.now());

        postRepository.save(post);
    }

    @Override
    public GetPostsResponse getPosts(GetPostsModel model, int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        PersonToPublicRelation relation = personToPublicRelationRepository.getByPublicIdAndPersonId(model.getPublicId(), personId);

        Page<PublicPost> posts;
        boolean showAll = relation != null && relation.getRelation() != PersonToPublicRelationType.SUBSCRIBER;
        Pageable pageable = PageRequest.of(model.getPage(), model.getCount());
        if (showAll) {
            posts = postRepository.getByPublicId(model.getPublicId(), pageable);
        } else {
            posts = postRepository.getPublishedByPublicId(model.getPublicId(), pageable);
        }

        return new GetPostsResponse(
                posts.getNumberOfElements(),
                posts.stream().map(p -> {
                    GetPostsResponse.PostItem item = new GetPostsResponse.PostItem(
                            p.getId(),
                            p.getAuthor().getFullName(),
                            p.getAuthor().getId(),
                            p.getText(),
                            p.getPublished()
                    );

                    if (showAll) {
                        item.setPublished(p.isPublished());
                        item.setCreated(p.getCreated());
                    }

                    return item;
                }).collect(Collectors.toList())
        );
    }

}
