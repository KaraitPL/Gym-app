package com.example.gymapp.gym.model;

import com.example.gymapp.gym.entity.GymType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GymCreateModel {
    private UUID id;
    private String name;
    private int numberOfEquipment;
    private GymType gymType;
}
