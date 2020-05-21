package com.danielhughes.personcrud.service;

import com.danielhughes.personcrud.domain.Person;
import com.danielhughes.personcrud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> listAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public Person put(Person inputPerson, Long id){
        Optional<Person> personOptional = personRepository.findById(id);

        if(personOptional.isPresent()){
            Person person = personOptional.get();
            if(inputPerson.getFirstName() != null){
                person.setFirstName(inputPerson.getFirstName());
            }
            if(inputPerson.getLastName() != null){
                person.setLastName(inputPerson.getLastName());
            }
            if(inputPerson.getAge() > 0 && inputPerson.getAge() < 100){
                person.setAge(inputPerson.getAge());
            }
            if(inputPerson.getEmail() != null){
                person.setEmail(inputPerson.getEmail());
            }
            if(inputPerson.getCity() != null){
                person.setCity(inputPerson.getCity());
            }
            if(inputPerson.getOccupation() != null){
                person.setOccupation(inputPerson.getOccupation());
            }
            personRepository.save(person);
            return person;
        }
        throw new NoSuchElementException("No person found in the database with id " + id);
    }

    public Person get(long id) throws NoSuchElementException{
        return personRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void delete(long id){
        personRepository.deleteById(id);
    }
}
