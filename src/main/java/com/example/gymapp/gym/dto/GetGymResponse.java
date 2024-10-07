package com.example.gymapp.gym.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetGymResponse {
    private UUID id;
    private String name;
    private Integer numberOfEquipment;
}
