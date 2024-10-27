package com.example.gymapp.gym.model;

import com.example.gymapp.gym.entity.GymType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GymEditModel {
    private String name;
    private int numberOfEquipment;
    private GymType gymType;
}