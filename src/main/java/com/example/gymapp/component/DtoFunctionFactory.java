package com.example.gymapp.component;

import com.example.gymapp.gym.dto.function.GymToResponseFunction;
import com.example.gymapp.gym.dto.function.GymsToResponseFunction;
import com.example.gymapp.gym.dto.function.RequestToGymFunction;
import com.example.gymapp.gym.dto.function.UpdateGymWithRequestFunction;
import com.example.gymapp.member.dto.function.MemberToResponseFunction;
import com.example.gymapp.member.dto.function.MembersToResponseFunction;
import com.example.gymapp.member.dto.function.RequestToMemberFunction;
import com.example.gymapp.member.dto.function.UpdateMemberWithRequestFunction;
import com.example.gymapp.trainer.dto.function.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {

    public TrainerToResponseFunction trainerToResponse(){
        return new TrainerToResponseFunction();
    }

    public TrainersToResponseFunction trainersToResponse(){
        return new TrainersToResponseFunction();
    }

    public RequestToTrainerFunction requestToTrainer(){
        return new RequestToTrainerFunction();
    }

    public UpdateTrainerWithRequestFunction updateTrainer(){
        return new UpdateTrainerWithRequestFunction();
    }

    public MembersToResponseFunction membersToResponse(){
        return new MembersToResponseFunction();
    }
    public MemberToResponseFunction memberToResponse() { return new MemberToResponseFunction(); }

    public RequestToMemberFunction requestToMember() { return new RequestToMemberFunction(); }
    public UpdateMemberWithRequestFunction updateMember() { return new UpdateMemberWithRequestFunction(); }
    public GymToResponseFunction gymToResponse() { return new GymToResponseFunction(); }

    public GymsToResponseFunction gymsToResponse() { return new GymsToResponseFunction(); }

    public RequestToGymFunction requestToGym() { return new RequestToGymFunction(); }

    public UpdateGymWithRequestFunction updateGym() { return new UpdateGymWithRequestFunction(); }

}

