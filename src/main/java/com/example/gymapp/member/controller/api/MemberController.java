package com.example.gymapp.member.controller.api;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;

import java.util.UUID;

public interface MemberController {

    GetMembersResponse getMembers();

    GetMembersResponse getGymMembers(UUID id);

    GetMembersResponse getTrainerMembers(UUID id);

    GetMemberResponse getMember(UUID uuid);

    void putMember(UUID id, PutMemberRequest request);

    void patchMember(UUID id, PatchMemberRequest request);

    void deleteMember(UUID id);

}
