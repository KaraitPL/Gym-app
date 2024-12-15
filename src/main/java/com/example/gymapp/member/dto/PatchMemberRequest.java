package com.example.gymapp.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchMemberRequest {
    private String name;
    private int benchPressMax;
    private Long version;
}
