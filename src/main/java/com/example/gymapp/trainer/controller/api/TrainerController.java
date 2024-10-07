package com.example.gymapp.trainer.controller.api;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;

import java.util.UUID;

public interface TrainerController {

    GetTrainerResponse getTrainer(UUID id);

    GetTrainersResponse getTrainers();

}
