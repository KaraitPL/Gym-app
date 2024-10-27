package com.example.gymapp.gym.model;

import com.example.gymapp.gym.entity.GymType;
import com.example.gymapp.member.entity.Member;
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
public class GymModel {
    private UUID id;
    private String name;
    private int numberOfEquipment;
    private GymType gymType;
    private List<Member> members;
}
