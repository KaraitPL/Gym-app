package com.example.gymapp.gym.controller.api;

import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.dto.PatchGymRequest;
import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.member.dto.GetMembersResponse;

import java.util.UUID;

public interface GymController {

    GetGymResponse getGym(UUID id);

    GetGymsResponse getGyms();

    void putGym(UUID id, PutGymRequest request);

    void patchGym(UUID id, PatchGymRequest request);

    void deleteGym(UUID id);



}
