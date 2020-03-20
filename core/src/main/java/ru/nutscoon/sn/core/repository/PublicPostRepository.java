package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PublicPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicPostRepository extends CrudRepository<PublicPost, Integer> {
    @Query("select p from PublicPost p where p.isPublished = true and p.publicId = :publicId")
    Page<PublicPost> getPublishedByPublicId(@Param("publicId") int publicId, Pageable pageable);
    Page<PublicPost> getByPublicId(int publicId, Pageable pageable);
}
