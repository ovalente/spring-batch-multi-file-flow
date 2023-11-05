package com.example.springbatchmultifile.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.springbatchmultifile.entity.PersonEntity;
import com.example.springbatchmultifile.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PersonEntityWriter implements ItemWriter<PersonEntity> {
    private final PersonRepository personRepository;

    @Override
    public void write(Chunk<? extends PersonEntity> chunk) throws Exception {
        log.info("Writing: {} person", chunk.getItems().size());
        personRepository.saveAll(chunk.getItems());
    }

    
}
