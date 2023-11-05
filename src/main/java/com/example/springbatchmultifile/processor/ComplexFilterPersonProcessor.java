package com.example.springbatchmultifile.processor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.springbatchmultifile.entity.PersonEntity;

@Component
public class ComplexFilterPersonProcessor implements ItemProcessor<PersonEntity, PersonEntity> {

    @Override
    @Nullable
    public PersonEntity process(PersonEntity item) {
        if (StringUtils.equalsAnyIgnoreCase( item.getTitle(), "professor")) {
            return item;
        } else {
            return null;
        }
    }
}
