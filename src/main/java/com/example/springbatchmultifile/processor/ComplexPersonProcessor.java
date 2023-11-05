package com.example.springbatchmultifile.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.springbatchmultifile.entity.PersonEntity;
import com.example.springbatchmultifile.mapper.PersonMapper;
import com.example.springbatchmultifile.model.PersonComplexDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ComplexPersonProcessor implements ItemProcessor<PersonComplexDTO, PersonEntity> {
    
    private final PersonMapper personMapper;

    @Override
    @Nullable
    public PersonEntity process(PersonComplexDTO item) {
        return personMapper.fromPersonComplexToPersonEntity(item);
    }
}
