package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.AccessDeniedException;
import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelation;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelationType;
import ru.nutscoon.sn.core.model.entity.Public;
import ru.nutscoon.sn.core.model.request.*;
import ru.nutscoon.sn.core.model.response.FindPublicResponse;
import ru.nutscoon.sn.core.model.response.PublicInfoResponse;
import ru.nutscoon.sn.core.model.response.PublicSubscriber;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonToPublicRelationRepository;
import ru.nutscoon.sn.core.repository.PublicRepository;
import ru.nutscoon.sn.core.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PublicServiceImpl extends BaseService implements PublicService {

    private final PublicRepository publicRepository;
    private final PersonToPublicRelationRepository personToPublicRelationRepository;


    @Autowired
    public PublicServiceImpl(
            PersonRepository personRepository,
            PublicRepository publicRepository,
            PersonToPublicRelationRepository personToPublicRelationRepository) {
        super(personRepository);
        this.publicRepository = publicRepository;
        this.personToPublicRelationRepository = personToPublicRelationRepository;
    }


    @Override
    public int create(CreatePublicModel model, int personId) {
        Person person = getPerson(personId);

        Public p = new Public();
        p.setName(model.getName());
        p.setDescription(model.getDescription());
        p.setCreated(LocalDateTime.now());
        p = publicRepository.save(p);

        PersonToPublicRelation relation = new PersonToPublicRelation();
        relation.setPublicId(p.getId());
        relation.setPerson(person);
        relation.setRelation(PersonToPublicRelationType.OWNER);
        personToPublicRelationRepository.save(relation);

        return p.getId();
    }

    @Override
    public void update(UpdatePublicModel model, int personId) {
        if (!personToPublicRelationRepository.isOwner(model.getId(), personId)) {
            throw new AccessDeniedException();
        }

        Public p = publicRepository.findById(model.getId()).orElseThrow(() -> new NotFoundException("Public not found"));
        p.setDescription(model.getDescription());
        publicRepository.save(p);
    }

    @Override
    public FindPublicResponse find(FindPublicModel model) {
        Page<Public> publics = publicRepository.findByName(model.getName(), PageRequest.of(model.getPage(), model.getCount()));
        return new FindPublicResponse(
                publics.getNumberOfElements(),
                publics.stream()
                        .map(p -> new FindPublicResponse.FindPublicItem(p.getId(), p.getName(), p.getDescription()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public FindPublicResponse getPublics(PublicRelationType relationType, int personId) {
        Collection<Public> publics;

        switch (relationType) {
            case OWNER:
                publics = publicRepository.getByPersonIdAndRelation(personId, PersonToPublicRelationType.OWNER);
                break;
            case ADMIN:
                publics = publicRepository.getByPersonIdAndRelation(personId, PersonToPublicRelationType.ADMIN);
                break;
            case ALL:
                publics = publicRepository.getByPersonId(personId);
                break;
            default:
                throw new InvalidOperationException("Unsupported relation type");
        }

        return new FindPublicResponse(
                publics.size(),
                publics.stream()
                        .map(p -> new FindPublicResponse.FindPublicItem(p.getId(), p.getName(), p.getDescription()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void subscribe(int personId, int publicId) {
        Person person = getPerson(personId);
        if (personToPublicRelationRepository.existsByPublicIdAndPersonId(publicId, person.getId())) {
            throw new InvalidOperationException("Already subscribed");
        }

        PersonToPublicRelation relation = new PersonToPublicRelation();
        relation.setPublicId(publicId);
        relation.setPerson(person);
        relation.setRelation(PersonToPublicRelationType.SUBSCRIBER);

        personToPublicRelationRepository.save(relation);
    }

    @Override
    @Transactional
    public void unsubscribe(int personId, int publicId) {
        if(!personRepository.existsById(personId)){
            throw new NotFoundException("Person not found");
        }

        PersonToPublicRelation relation = personToPublicRelationRepository.getByPublicIdAndPersonId(publicId, personId);
        if (relation == null) {
            throw new InvalidOperationException("Not subscribed");
        }

        if (relation.getRelation() == PersonToPublicRelationType.OWNER) {
            throw new InvalidOperationException("Unable unsubscribe. You are admin");
        }

        personToPublicRelationRepository.delete(relation);
    }

    @Override
    public void assignAdminRights(MakePublicAdminModel model, int ownerId) {
        if (!personToPublicRelationRepository.isOwner(model.getPublicId(), ownerId)) {
            throw new AccessDeniedException();
        }

        PersonToPublicRelation relation = personToPublicRelationRepository.getByPublicIdAndPersonId(model.getPublicId(), model.getPersonId());
        if(relation == null){
            throw new NotFoundException("Person not subscribed to public");
        }

        if (model.IsAdmin()) {
            relation.setRelation(PersonToPublicRelationType.ADMIN);
        } else {
            relation.setRelation(PersonToPublicRelationType.SUBSCRIBER);
        }

        personToPublicRelationRepository.save(relation);
    }

    @Override
    public PublicInfoResponse getPublicInfo(int publicId) {
        Public p = publicRepository.findById(publicId).orElseThrow(() -> new NotFoundException("Public not found"));
        PersonToPublicRelation owner = personToPublicRelationRepository
                .getByPublicIdAndRelation(publicId, PersonToPublicRelationType.OWNER)
                .stream()
                .findFirst().orElseThrow(() -> new RuntimeException(String.format("Public %d has no owner", publicId)));
        Collection<PersonToPublicRelation> admin = personToPublicRelationRepository
                .getByPublicIdAndRelation(publicId, PersonToPublicRelationType.ADMIN);

        return new PublicInfoResponse(
                p.getName(),
                p.getDescription(),
                getPublicSubscriber(owner.getPerson()),
                admin.stream().map(x -> getPublicSubscriber(x.getPerson())).collect(Collectors.toList()),
                p.getCreated()
        );
    }

    public Collection<PublicSubscriber> getSubscribers(int publicId) {
        return personToPublicRelationRepository
                .getByPublicId(publicId).stream()
                .map(x -> getPublicSubscriber(x.getPerson()))
                .collect(Collectors.toList());
    }

    private PublicSubscriber getPublicSubscriber(Person person){
        return new PublicSubscriber(person.getId(), person.getFullName());
    }
}
