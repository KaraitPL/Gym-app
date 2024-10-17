package com.example.gymapp.member.controller.api;

import com.example.gymapp.member.dto.GetMembersResponse;

import java.util.UUID;

public interface MemberController {
    GetMembersResponse getTrainerMembers(UUID id);
}
