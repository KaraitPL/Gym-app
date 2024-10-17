package com.example.gymapp.component;

import com.example.gymapp.member.dto.function.MembersToResponseFunction;
import com.example.gymapp.trainer.dto.function.*;

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
    /*public MemberToResponseFunction memberToResponse() {
        return new MemberToResponseFunction();
    }

    public MembersToResponseFunction membersToResponse() {
        return new MembersToResponseFunction();
    }

    public GymToResponseFunction gymToResponse() {
        return new GymToResponseFunction();
    }

    public GymsToResponseFunction gymsToResponse() {
        return new GymsToResponseFunction();
    }

    public RequestToMemberFunction requestToMember() {
        return new RequestToMemberFunction();
    }

    public UpdateMemberWithRequestFunction updateMember() {
        return new UpdateMemberWithRequestFunction();
    }

    public RequestToTrainerFunction requestToTrainer() {
        return new RequestToTrainerFunction();
    }

    public UpdateTrainerWithRequestFunction updateTrainer() {
        return new UpdateTrainerWithRequestFunction();
    }

    public UpdateTrainerPasswordWithRequestFunction updateTrainerPassword() {
        return new UpdateTrainerPasswordWithRequestFunction();
    }

    public TrainersToResponseFunction trainersToResponse() {
        return new TrainersToResponseFunction();
    }

    public TrainerToResponseFunction trainerToResponse() {
        return new TrainerToResponseFunction();
    }*/

}

