package com.example.gymapp.member.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MembersModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member{
        private UUID id;
        private String name;
        private int benchPressMax;
        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime modifiedDateTime;
    }

    @Singular
    private List<Member> members;
}
