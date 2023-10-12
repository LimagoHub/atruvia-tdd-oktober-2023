package de.atruvia.service;

import de.atruvia.persistence.Person;

public interface PersonenService {
    void speichern(Person person) throws PersonenServiceException;
}
