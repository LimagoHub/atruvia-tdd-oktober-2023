package de.atruvia.myweb.mywebapp.domain.inner;

import de.atruvia.myweb.mywebapp.domain.PersonService;
import de.atruvia.myweb.mywebapp.domain.PersonenServiceException;
import de.atruvia.myweb.mywebapp.domain.mapper.PersonMapper;
import de.atruvia.myweb.mywebapp.domain.model.Person;
import de.atruvia.myweb.mywebapp.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = PersonenServiceException.class, isolation = Isolation.READ_COMMITTED)
public class PersonenServiceImpl implements PersonService {

    private final PersonRepository repo;
    private final PersonMapper mapper;

    private final List<String> antipathen;

    /*
            person null -> PSE
            vorname null vorname kleiner 2 -> PSE
            nachname null nachname kleiner 2 -> PSE

            Attila -> PSE

            technische Exception -> PSE

            happy Day -> person to repo
     */
    @Override
    public boolean speichernOderAendern(Person person) throws PersonenServiceException {
        try {
            if(person == null)
                throw new PersonenServiceException("parameter darf nicht null sein.");

            if(person.getVorname() == null || person.getVorname().length() < 2)
                throw new PersonenServiceException("vorname zu kurz.");

            if(person.getNachname() == null || person.getNachname().length() < 2)
                throw new PersonenServiceException("namename zu kurz.");

            if(antipathen.contains(person.getVorname()))
                throw new PersonenServiceException("Antipath");

            boolean exists = repo.existsById(person.getId());

            repo.save(mapper.convert(person));

            return exists;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Upps",e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Optional<Person> findeNachId(String id) throws PersonenServiceException {
        try {
           return repo.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Upps",e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert( repo.findAll());
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Upps",e);
        }
    }

    @Override
    public boolean loeschen(String id) throws PersonenServiceException {

        try {
            if(repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Upps",e);
        }
    }
}
