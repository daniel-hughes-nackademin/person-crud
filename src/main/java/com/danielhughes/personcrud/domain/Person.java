package com.danielhughes.personcrud.domain;


import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person extends RepresentationModel<Person>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Person(@NotBlank @Size(min = 2, max = 20) String firstName, @NotBlank @Size(min = 2, max = 20) String lastName, @Range(min = 0, max = 100) int age, @Email String email, @NotBlank @Size(min = 2, max = 20) String city, @NotBlank @Size(min = 2, max = 20) String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.city = city;
        this.occupation = occupation;
    }

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;

    @Range(min = 0, max = 100)
    private int age;

    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    private String city;

    @NotBlank
    @Size(min = 2, max = 20)
    private String occupation;
}
