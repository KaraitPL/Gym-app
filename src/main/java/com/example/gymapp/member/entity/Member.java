package com.example.gymapp.member.entity;

import com.example.gymapp.entity.VersionAndCreationDateAuditable;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.trainer.entity.Trainer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "members")
public class Member extends VersionAndCreationDateAuditable implements Serializable {
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

    @Override
    public void updateCreationDateTime()
    {
        super.updateCreationDateTime();
    }
    @Override
    public void updateEditDateTime()
    {
        super.updateEditDateTime();
    }
}
