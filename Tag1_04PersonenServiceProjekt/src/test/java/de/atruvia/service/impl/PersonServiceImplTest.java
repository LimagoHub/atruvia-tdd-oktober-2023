package de.atruvia.service.impl;

import de.atruvia.persistence.Person;
import de.atruvia.persistence.PersonenRepository;
import de.atruvia.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository personenRepositoryMock;


    private static final Person VALID_PERSON = Person.builder().vorname("John").nachname("Doe").build();
    @Test
    void speichern_personIsNull_throwsPersonServiceException () {
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(null));
        assertEquals("Person darf nicht null sein.", ex.getMessage());

    }

    @Test
    void speichern_firstNameIsNull_throwsPersonServiceException () {
        final Person invalidPerson = Person.builder().vorname(null).nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname zu kurz.", ex.getMessage());

    }

    @Test
    void speichern_firstNameToShort_throwsPersonServiceException () {
        final Person invalidPerson = Person.builder().vorname("J").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname zu kurz.", ex.getMessage());

    }

    @Test
    void speichern_lastNameIsNull_throwsPersonServiceException () {
        final Person invalidPerson = Person.builder().vorname("John").nachname(null).build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(invalidPerson));
        assertEquals("Nachname zu kurz.", ex.getMessage());

    }

    @Test
    void speichern_lastNameToShort_throwsPersonServiceException () {
        final Person invalidPerson = Person.builder().vorname("John").nachname("D").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(invalidPerson));
        assertEquals("Nachname zu kurz.", ex.getMessage());

    }

    @Test
    void speichern_blacklistedPerson_throwsPersonServiceException () {
        final Person invalidPerson = Person.builder().vorname("Attila").nachname("der Hunne").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(invalidPerson));
        assertEquals("Person steht auf der schwarzen Liste.", ex.getMessage());

    }

    @Test
    void speichern_unexcpetedRuntimeExceptionInUnderlyingService_throwsPersonServiceException () {
        doThrow(new IndexOutOfBoundsException(10)).when(personenRepositoryMock).save(any(Person.class));
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(VALID_PERSON));
        assertEquals("Ein Fehler ist aufgetreten.", ex.getMessage());
        assertEquals(IndexOutOfBoundsException.class, ex.getCause().getClass());

    }
    @Test
    void speichern_HappyDay_personPassedToRepo () {
        doNothing().when(personenRepositoryMock).save(any(Person.class));
        assertDoesNotThrow( ()->objectUnderTest.speichern(VALID_PERSON));

        verify(personenRepositoryMock, times(1)).save(VALID_PERSON);

    }




}