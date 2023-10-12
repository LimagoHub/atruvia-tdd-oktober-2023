package de.atruvia.myweb.mywebapp.domain;

import de.atruvia.myweb.mywebapp.domain.model.Person;

public interface BlacklistService {

    boolean isBlacklisted(Person person);
}
