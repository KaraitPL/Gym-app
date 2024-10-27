package com.example.gymapp.trainer.entity;

import com.example.gymapp.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Trainer implements Serializable {
    private UUID id;
    private String name;
    private int yearsOfTraining;
    private LocalDate birthDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Member> members;
}
