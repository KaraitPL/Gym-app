package com.example.gymapp.member.entity;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.trainer.entity.Trainer;
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
public class Member implements Serializable {
    private UUID id;
    private String name;
    private Integer age;
    private Gym gym;
    private Trainer trainer;
}
