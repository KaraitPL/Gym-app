package com.example.gymapp.member.entity;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.trainer.entity.Trainer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.swing.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@ToString//(callSuper = true)
@EqualsAndHashCode//(callSuper = true)
@Entity
@Table(name = "members")
public class Member implements Serializable {
    @Id
    private UUID id;
    private String name;
    private int benchPressMax;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Gym gym;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer;
}
