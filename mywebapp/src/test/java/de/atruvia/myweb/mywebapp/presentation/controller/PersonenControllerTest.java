package de.atruvia.myweb.mywebapp.presentation.controller;

import de.atruvia.myweb.mywebapp.domain.PersonService;
import de.atruvia.myweb.mywebapp.domain.model.Person;
import de.atruvia.myweb.mywebapp.presentation.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql({"/create.sql", "/insert.sql"})
@ExtendWith(SpringExtension.class)
class PersonenControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonService serviceMock;
    @Test
    void test1() throws Exception{

        // Arrange
        final Optional<Person> optionalPerson = Optional.of(Person.builder().id("1").vorname("John").nachname("Doe").build());
        when(serviceMock.findeNachId(anyString())).thenReturn(optionalPerson);


        var dto = restTemplate.getForObject("/v1/personen/stringstringstringstringstringstr001", PersonDTO.class);

        assertEquals("John",dto.getVorname());
    }

    @Test
    void test2() throws Exception{

        // Arrange
        final Optional<Person> optionalPerson = Optional.of(Person.builder().id("1").vorname("John").nachname("Doe").build());
        when(serviceMock.findeNachId(anyString())).thenReturn(optionalPerson);


        var dto = restTemplate.getForObject("/v1/personen/stringstringstringstringstringstr001", String.class);

        System.out.println(dto);
    }

    @Test
    void test3() throws Exception{

        // Arrange
        final Optional<Person> optionalPerson = Optional.of(Person.builder().id("1").vorname("John").nachname("Doe").build());
        when(serviceMock.findeNachId(anyString())).thenReturn(optionalPerson);


        var responseEntity = restTemplate.getForEntity("/v1/personen/stringstringstringstringstringstr001", PersonDTO.class);

        var dto = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test4() throws Exception{

        // Arrange
        final Optional<Person> optionalPerson = Optional.empty();
        when(serviceMock.findeNachId(anyString())).thenReturn(optionalPerson);


        var responseEntity = restTemplate.exchange("/v1/personen/stringstringstringstringstringstr001",
                HttpMethod.GET,
                null,
                PersonDTO.class);

        var dto = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void test5() throws Exception{
        Iterable<Person> personen = List.of(
                Person.builder().id("1").vorname("John").nachname("Doe").build(),
                Person.builder().id("2").vorname("John").nachname("Rambo").build());

        when(serviceMock.findeAlle()).thenReturn(personen);
        ResponseEntity<Iterable<PersonDTO>> entity = restTemplate.exchange("/v1/personen",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<PersonDTO>>() { });
        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals(2, StreamSupport.stream(entity.getBody().spliterator(), false).count());

    }
    @Test
    void test6() throws Exception{

        var p = PersonDTO.builder().id("1").vorname("John").nachname("Doe").build();
        HttpEntity<PersonDTO> requestEntity = new HttpEntity<>(p);

        Iterable<Person> personen = List.of(
                Person.builder().id("1").vorname("John").nachname("Doe").build(),
                Person.builder().id("2").vorname("John").nachname("Rambo").build());

        when(serviceMock.findeAlle()).thenReturn(personen);
        ResponseEntity<Iterable<PersonDTO>> entity = restTemplate.exchange("/v1/personen",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Iterable<PersonDTO>>() { });
        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals(2, StreamSupport.stream(entity.getBody().spliterator(), false).count());

    }


}