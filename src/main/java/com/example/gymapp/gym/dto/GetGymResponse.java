package com.example.gymapp.gym.dto;

import com.example.gymapp.gym.entity.GymType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetGymResponse {

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
    private int numberOfEquipment;
    private GymType gymType;
    //private List<Member> members;
}
