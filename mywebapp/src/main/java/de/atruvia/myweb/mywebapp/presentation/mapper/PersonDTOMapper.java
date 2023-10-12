package de.atruvia.myweb.mywebapp.presentation.mapper;

import de.atruvia.myweb.mywebapp.domain.model.Person;
import de.atruvia.myweb.mywebapp.presentation.dto.PersonDTO;
import de.atruvia.myweb.mywebapp.repositories.entities.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {

    PersonDTO convert(Person person);
    Person convert(PersonDTO person);
    Iterable<PersonDTO> convert(Iterable<Person> personen);
}
