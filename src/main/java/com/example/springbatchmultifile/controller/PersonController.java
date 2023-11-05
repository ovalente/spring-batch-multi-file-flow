package com.example.springbatchmultifile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbatchmultifile.model.PersonDTO;
import com.example.springbatchmultifile.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    
    private final PersonService personService;

    @GetMapping
    public List<PersonDTO> getAllPerson() {
        log.info("Handling find all person");
        return personService.findAllPerson();
    }
}
