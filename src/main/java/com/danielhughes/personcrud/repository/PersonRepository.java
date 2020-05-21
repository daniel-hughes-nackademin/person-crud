package com.danielhughes.personcrud.repository;

import com.danielhughes.personcrud.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
