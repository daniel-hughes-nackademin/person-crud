package com.danielhughes.personcrud.controller;

import com.danielhughes.personcrud.domain.Person;
import com.danielhughes.personcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class AppController {
    private final PersonService personService;

    @Autowired
    public AppController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/")
    public String viewIndexPage(Model model){
        List<Person> personList = personService.listAll();
        model.addAttribute("personList", personList);
        return "index";
    }

    @GetMapping("/new")
    public String viewNewPersonPage(Model model){
        Person person = new Person();
        model.addAttribute("currentPerson", person);
        return "new-person";
    }

    @PostMapping("/save")
        public String savePerson(@Valid Person person, BindingResult bindingResult, Model model){
        System.out.println(bindingResult.getModel().toString());
            if(bindingResult.hasErrors()){
                System.out.println(bindingResult.getAllErrors());
                viewNewPersonPage(model);
                return "new-person";
            }
            personService.save(person);
        return "redirect:/";
        }

    @RequestMapping("/edit/{id}")
    public String viewEditPersonPage(@PathVariable long id, Model model){
        Person person = personService.get(id);

        //TODO Try catch for no such element exception?

        model.addAttribute("currentPerson", person);
        return "edit-person";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id){
        personService.delete(id);
        return "redirect:/";
    }

}


