package de.atruvia.myweb.mywebapp.domain;

import de.atruvia.myweb.mywebapp.domain.model.Person;

import java.util.Optional;

public interface PersonService {

    boolean speichernOderAendern(Person person) throws PersonenServiceException;
    Optional<Person> findeNachId(String id)throws PersonenServiceException;
    Iterable<Person> findeAlle() throws PersonenServiceException;
    boolean loeschen(String id) throws PersonenServiceException;

}
