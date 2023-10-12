package de.atruvia.service.impl;

import de.atruvia.persistence.Person;
import de.atruvia.persistence.PersonenRepository;
import de.atruvia.service.BlacklistService;
import de.atruvia.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository personenRepositoryMock;

    @Mock
    private BlacklistService blacklistServiceMock;

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
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenReturn(true);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(VALID_PERSON));
        assertEquals("Person steht auf der schwarzen Liste.", ex.getMessage());

    }

    @Test
    void speichern_unexpectedRuntimeExceptionInBlacklistService_throwsPersonServiceException () {
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenThrow(NumberFormatException.class);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(VALID_PERSON));
        assertEquals("Ein Fehler ist aufgetreten.", ex.getMessage());
        assertEquals(NumberFormatException.class, ex.getCause().getClass());

    }

    @Test
    void speichern_unexcpetedRuntimeExceptionInUnderlyingService_throwsPersonServiceException () {
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenReturn(false);
        doThrow(new IndexOutOfBoundsException(10)).when(personenRepositoryMock).save(any(Person.class));
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(VALID_PERSON));
        assertEquals("Ein Fehler ist aufgetreten.", ex.getMessage());
        assertEquals(IndexOutOfBoundsException.class, ex.getCause().getClass());

    }
    @Test
    void speichern_HappyDay_personPassedToRepo () {
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenReturn(false);
        doNothing().when(personenRepositoryMock).save(any(Person.class));
        assertDoesNotThrow( ()->objectUnderTest.speichern(VALID_PERSON));

        ArgumentCaptor<Person> peopleCaptor = ArgumentCaptor.forClass(Person.class);

        InOrder order = inOrder(blacklistServiceMock, personenRepositoryMock);

        order.verify(blacklistServiceMock).isBlacklisted(peopleCaptor.capture());

        assertNotNull(peopleCaptor.getValue().getId());
        assertEquals(36, peopleCaptor.getValue().getId().length());
        assertEquals("John", peopleCaptor.getValue().getVorname());
        assertEquals("Doe", peopleCaptor.getValue().getNachname());

        order.verify(personenRepositoryMock, times(1)).save(peopleCaptor.capture());


    }

    @Test
    void speichern_HappyDayUUID_personPassedToRepo () {
        UUID result = UUID.fromString("8cf917c5-8069-479d-bb8e-ed76658d5b85");
        try (var mocked = mockStatic(UUID.class)) {
            //ARRANGE
            final Person expectedSavedPerson = Person.builder().vorname("John").nachname("Doe").id("8cf917c5-8069-479d-bb8e-ed76658d5b85").build();
            mocked.when(UUID::randomUUID).thenReturn(result);
            when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenReturn(false);
            doNothing().when(personenRepositoryMock).save(any(Person.class));

            //ACT
            assertDoesNotThrow(() -> objectUnderTest.speichern(VALID_PERSON));


            //ASSERT
            verify(personenRepositoryMock, times(1)).save(expectedSavedPerson);
        }
    }

    @Test
    void speichern_Lambda_personPassedToRepo () {
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenAnswer(invocationOnMock -> {
            return false;
        });
        doAnswer(invocationOnMock -> {
            Person capturedPerson = invocationOnMock.getArgument(0);
            assertNotNull(capturedPerson.getId());
            assertEquals(36, capturedPerson.getId().length());
            assertEquals("John", capturedPerson.getVorname());
            assertEquals("Doe", capturedPerson.getNachname());
            return null;
        }).when(personenRepositoryMock).save(any(Person.class));
        assertDoesNotThrow( ()->objectUnderTest.speichern(VALID_PERSON));



    }

}