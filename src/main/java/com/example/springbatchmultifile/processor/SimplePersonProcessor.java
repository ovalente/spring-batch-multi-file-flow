package com.example.springbatchmultifile.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.springbatchmultifile.entity.PersonEntity;
import com.example.springbatchmultifile.mapper.PersonMapper;
import com.example.springbatchmultifile.model.PersonSimpleDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SimplePersonProcessor implements ItemProcessor<PersonSimpleDTO, PersonEntity> {
    private final PersonMapper personMapper;

    @Override
    @Nullable
    public PersonEntity process(PersonSimpleDTO item) {
        return personMapper.fromPersonSimpleToPersonEntity(item);
    }
}
