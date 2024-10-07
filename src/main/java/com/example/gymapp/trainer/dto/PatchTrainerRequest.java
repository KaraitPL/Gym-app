package com.example.gymapp.trainer.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchTrainerRequest {
    private String name;
    private LocalDate birthDate;
}
