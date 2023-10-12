package de.atruvia.myweb.mywebapp.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;


@Data
@Builder
public class Person {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    private String vorname;

    private String nachname;
}
