package com.example.gymapp.gym.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchGymRequest {
    private String name;
    private int numberOfEquipment;
}
