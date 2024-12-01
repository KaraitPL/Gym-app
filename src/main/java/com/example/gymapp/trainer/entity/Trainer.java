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
@EqualsAndHashCode(exclude = "members")
@Entity
@Table(name = "trainers")
public class Trainer implements Serializable {
    @Id
    private UUID id;
    private String name;
    private int yearsOfTraining;
    private LocalDate birthDate;

    @ToString.Exclude
    private String password;

    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


    @ToString.Exclude
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Member> members;
}
