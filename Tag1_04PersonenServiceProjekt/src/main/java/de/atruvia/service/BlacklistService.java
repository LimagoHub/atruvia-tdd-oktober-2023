package de.atruvia.service;

import de.atruvia.persistence.Person;

public interface BlacklistService {

    boolean isBlacklisted(Person possibleBlacklistedPerson);
}
