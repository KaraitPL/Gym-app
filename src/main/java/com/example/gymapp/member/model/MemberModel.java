package com.example.gymapp.member.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MemberModel {
    private UUID id;
    private String name;
    private int benchPressMax;
    private String trainer;
    private String Gym;
    private Long version;

    private LocalDateTime creationDateTime;
}
