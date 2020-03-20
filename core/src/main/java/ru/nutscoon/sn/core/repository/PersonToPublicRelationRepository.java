package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PersonToPublicRelation;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonToPublicRelationRepository extends CrudRepository<PersonToPublicRelation, Integer> {
    @Query("select CASE  WHEN count(p)> 0 THEN true ELSE false end " +
            "from PersonToPublicRelation p where p.person.id = :personId " +
            "and p.publicId = :publicId " +
            "and p.relation = 0")
    boolean isOwner(@Param("publicId") int publicId, @Param("personId") int personId);
    boolean existsByPublicIdAndPersonId(int publicId, int personId);
    PersonToPublicRelation getByPublicIdAndPersonId(int publicId, int personId);
    Collection<PersonToPublicRelation> getByPublicIdAndRelation(int publicId, PersonToPublicRelationType relation);
    Collection<PersonToPublicRelation> getByPublicId(int publicId);
}
