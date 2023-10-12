package de.atruvia.service.impl;

import de.atruvia.persistence.Person;
import de.atruvia.persistence.PersonenRepository;
import de.atruvia.service.BlacklistService;
import de.atruvia.service.PersonenService;
import de.atruvia.service.PersonenServiceException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PersonServiceImpl implements PersonenService {


    private final PersonenRepository repo;
    private final BlacklistService blacklistService;

    /*
       1.) wenn person = null => PSE
       2.) wenn vorname null oder weniger als 2 Zeichen => PSE
       3.) wenn nachname null oder weniger als 2 Zeichen => PSE
       4.) wenn Vorname = Attila => PSE
       5.) wenn Laufzeitfehler => PSE

       Happy day => person via repo speichern
    */
    @Override
    public void speichern(final Person person) throws PersonenServiceException {
        try {
            speichernImpl(person);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten.",e);
        }


    }

    @Override
    public void speichern(final String vorname, final String nachname) throws PersonenServiceException {
        speichern(Person.builder().vorname(vorname).nachname(nachname).build());
    }

    private void speichernImpl(final Person person) throws PersonenServiceException {
        checkPerson(person);
        person.setId(UUID.randomUUID().toString());
        repo.save(person);
    }

    private void checkPerson(final Person person) throws PersonenServiceException {
        validatePerson(person);
        businessCheck(person);
    }

    private void businessCheck(final Person person) throws PersonenServiceException {
        if(blacklistService.isBlacklisted(person))
            throw new PersonenServiceException("Person steht auf der schwarzen Liste.");
    }

    private static void validatePerson(final Person person) throws PersonenServiceException {
        if(person == null)
            throw new PersonenServiceException("Person darf nicht null sein.");
        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz.");
        if(person.getNachname() == null || person.getNachname().length() < 2)
            throw new PersonenServiceException("Nachname zu kurz.");
    }


}
