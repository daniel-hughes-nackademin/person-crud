package com.danielhughes.personcrud.controller;

import com.danielhughes.personcrud.domain.Person;
import com.danielhughes.personcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/person")
public class RestController {

    private final PersonService personService;

    @Autowired
    public RestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public HttpEntity<List<Person>> getPersonList(){
        final List<Person> personList = personService.listAll();
        personList.forEach(this::addLinks);

        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<Person> getPerson(@PathVariable long id){
        Person person = personService.get(id);
        addLinks(person);

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deletePerson(@PathVariable long id){
        personService.delete(id);
        return new ResponseEntity<>("Person with id " + id + " deleted from database", HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<Person> addPerson(@Valid @RequestBody Person person){
        personService.save(person);
        addLinks(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public HttpEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person person){

        person = personService.put(person, id);

        addLinks(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    private void addLinks(Person person){
        Link selfLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(
                                RestController.class)
                                .getPerson(person.getId()))
                                .withSelfRel();
        person.add(selfLink);

        Link personListLink = WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(
                                        RestController.class)
                                        .getPersonList())
                                        .withRel("personList");
        person.add(personListLink);

        Link updatePersonLink = WebMvcLinkBuilder.linkTo(
                                     WebMvcLinkBuilder.methodOn(
                                        RestController.class)
                                        .updatePerson(person.getId(), person))
                                        .withRel("updatePerson(put)");
        person.add(updatePersonLink);

        Link deletePersonLink = WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(
                                        RestController.class)
                                        .deletePerson(person.getId()))
                                        .withRel("deletePerson(delete)");
        person.add(deletePersonLink);

        Link addPersonLink = WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(
                                    RestController.class)
                                    .addPerson(person))
                                    .withRel("addPerson(post)");
        person.add(addPersonLink);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
