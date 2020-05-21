package com.danielhughes.personcrud.clr;

import com.danielhughes.personcrud.domain.Person;
import com.danielhughes.personcrud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner  implements org.springframework.boot.CommandLineRunner {

    private final PersonRepository personRepository;

    @Autowired
    public CommandLineRunner(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (personRepository.findAll().isEmpty()){
            personRepository.save(new Person("Daniel", "Hughes", 30, "daniel.hughes@yh.nackademin.se", "Stockholm", "Developer"));
            personRepository.save(new Person("Lisa", "Stewards", 19, "lisa213@hotmail.com", "Linköping", "Student"));
            personRepository.save(new Person("Gabriel", "Hernandes", 62, "mr-cool@cool-dude.com", "Los Angeles", "Painter"));
            personRepository.save(new Person("Yuki", "Miyamoto", 43, "info@sakura.jp", "Osaka", "Artist"));
        }
    }
}
