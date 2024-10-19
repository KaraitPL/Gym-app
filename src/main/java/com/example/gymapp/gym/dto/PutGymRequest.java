package com.example.gymapp.gym.dto;

import com.example.gymapp.gym.entity.GymType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutGymRequest {
    private String name;
    private int numberOfEquipment;
    private GymType gymType;
}
