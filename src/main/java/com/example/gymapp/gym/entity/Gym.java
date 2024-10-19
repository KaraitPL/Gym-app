package com.example.gymapp.gym.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class Gym implements Serializable {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member{
        private UUID id;
        private String name;
        private int benchPressMax;
    }
    private UUID id;
    private String name;
    private GymType gymType;
    private Integer numberOfEquipment;
    private List<Member> members;
}
