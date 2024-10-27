package com.example.gymapp.member.model;

import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.trainer.entity.Trainer;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MemberEditModel {
    private String name;
    private int benchPressMax;
    private Trainer trainer;
    private GymModel gym;
}
