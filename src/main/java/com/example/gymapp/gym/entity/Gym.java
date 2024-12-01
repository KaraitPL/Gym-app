package com.example.gymapp.gym.entity;

import com.example.gymapp.member.entity.Member;
import jakarta.persistence.*;
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
@ToString(callSuper = true)
@EqualsAndHashCode(exclude = "members")
@Entity
@Table(name = "gyms")
public class Gym implements Serializable {
    @Id
    private UUID id;
    private String name;
    private GymType gymType;
    private Integer numberOfEquipment;


    @ToString.Exclude
    @OneToMany(mappedBy = "gym", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Member> members;
}
