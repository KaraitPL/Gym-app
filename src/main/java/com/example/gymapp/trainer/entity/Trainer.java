package com.example.gymapp.trainer.entity;

import com.example.gymapp.member.entity.Member;
import jakarta.persistence.*;
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
@Entity
@Table(name = "trainers")
public class Trainer implements Serializable {
    @Id
    private UUID id;
    private String name;
    private int yearsOfTraining;
    private LocalDate birthDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<Member> members;
}
