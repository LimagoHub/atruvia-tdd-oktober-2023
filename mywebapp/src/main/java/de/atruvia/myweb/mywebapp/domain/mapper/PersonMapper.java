package de.atruvia.myweb.mywebapp.domain.mapper;

import de.atruvia.myweb.mywebapp.domain.model.Person;
import de.atruvia.myweb.mywebapp.repositories.entities.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person convert(PersonEntity entity);
    PersonEntity convert(Person person);
    Iterable<Person> convert(Iterable<PersonEntity> entities);
}
