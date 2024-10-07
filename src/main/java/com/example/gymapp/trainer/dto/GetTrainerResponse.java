package com.example.gymapp.trainer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTrainerResponse {
    private UUID id;
    private String login;
    private String name;
    private LocalDate birthDate;
}
