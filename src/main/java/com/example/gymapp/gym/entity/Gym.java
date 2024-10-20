package com.example.gymapp.gym.entity;

import com.example.gymapp.member.entity.Member;
import lombok.*;

import java.io.Serializable;
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

public class Gym implements Serializable {

    private UUID id;
    private String name;
    private GymType gymType;
    private Integer numberOfEquipment;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ArrayList<Member> members;
}
