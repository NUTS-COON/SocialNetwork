package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PersonToPublicRelationType;
import ru.nutscoon.sn.core.model.entity.Public;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PublicRepository extends CrudRepository<Public, Integer> {
    @Query("select p from Public p where :name is null or p.name like concat('%',:name,'%')")
    Page<Public> findByName(@Param("name") String name, Pageable pageable);

    @Query("select p from Public p " +
            "join PersonToPublicRelation ptp on p.id = ptp.publicId and ptp.person.id = :personId and ptp.relation = :relation")
    Collection<Public> getByPersonIdAndRelation(@Param("personId") int personId, @Param("relation") PersonToPublicRelationType relation);

    @Query("select p from Public p " +
            "join PersonToPublicRelation ptp on p.id = ptp.publicId and ptp.person.id = :personId")
    Collection<Public> getByPersonId(@Param("personId") int personId);
}
