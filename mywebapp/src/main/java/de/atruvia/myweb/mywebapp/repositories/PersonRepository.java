package de.atruvia.myweb.mywebapp.repositories;

import de.atruvia.myweb.mywebapp.repositories.entities.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, String> {

    Iterable<PersonEntity> findByVorname(String vorname);
}
