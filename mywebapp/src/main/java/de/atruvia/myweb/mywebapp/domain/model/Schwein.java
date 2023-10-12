package de.atruvia.myweb.mywebapp.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;

@Data
@Builder
public class Schwein {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    @Setter(AccessLevel.PRIVATE)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    private int gewicht;

    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }
}
