package com.example.springbatchmultifile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springbatchmultifile.mapper.PersonMapper;
import com.example.springbatchmultifile.model.PersonDTO;
import com.example.springbatchmultifile.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
    
    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    public List<PersonDTO> findAllPerson() {
        return personMapper.toPersonDTOList(personRepository.findAll());
    }

}
