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
    private Integer age;
    private UUID gym;
}
