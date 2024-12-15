package com.example.gymapp.member.model;

import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.trainer.entity.Trainer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MemberCreateModel {
    private UUID id;

    @NotBlank
    private String name;

    @Min(20)
    private int benchPressMax;

    @NotNull
    private Trainer trainer;

    @NotNull
    private GymModel gym;
}
