package com.example.gymapp.member.controller.api;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;

import java.util.UUID;

public interface MemberController {
    GetMembersResponse getTrainerMembers(UUID id);

    GetMembersResponse getGymMembers(UUID id);

    GetMemberResponse getMember(UUID id);

    GetMembersResponse getMembers();

    void putMember(UUID id, PutMemberRequest request);

    void patchMember(UUID id, PatchMemberRequest request);

    void deleteMember(UUID id);
}
