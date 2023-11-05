package com.example.springbatchmultifile.model;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private Integer age;
    private String title;
}
