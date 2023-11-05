package com.example.springbatchmultifile.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springbatchmultifile.entity.PersonEntity;
import com.example.springbatchmultifile.model.PersonComplexDTO;
import com.example.springbatchmultifile.model.PersonDTO;
import com.example.springbatchmultifile.model.PersonSimpleDTO;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", ignore = true)
    PersonEntity fromPersonSimpleToPersonEntity(PersonSimpleDTO source);

    @Mapping(target = "id", ignore = true)
    PersonEntity fromPersonComplexToPersonEntity(PersonComplexDTO source);

    PersonSimpleDTO toPersonSimpeDTO(PersonEntity source);

    PersonComplexDTO toPersonComplexDTO(PersonEntity source);

    PersonDTO toPersonDTO(PersonEntity source);

    List<PersonDTO> toPersonDTOList(List<PersonEntity> sources);
    
}
