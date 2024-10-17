package com.example.gymapp.member.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutMemberRequest {
    private String name;
    private int benchPressMax;
    private UUID gym;
    private UUID trainer;
}
