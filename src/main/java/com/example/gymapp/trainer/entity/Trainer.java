package com.example.gymapp.trainer.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Trainer implements Serializable {
    private UUID id;
    private String name;
    private String login;
    @ToString.Exclude
    private String password;
    private LocalDate birthDate;
    private List<String> genders;
}
