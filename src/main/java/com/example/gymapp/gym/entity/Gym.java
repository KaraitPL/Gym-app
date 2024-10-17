package com.example.gymapp.gym.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class Gym implements Serializable {
    private UUID id;
    private String name;
    private GymType gymType;
    private Integer numberOfEquipment;
}
