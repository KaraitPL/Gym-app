package com.example.gymapp.component;

import com.example.gymapp.gym.model.function.GymToEditModelFunction;
import com.example.gymapp.gym.model.function.GymToModelFunction;
import com.example.gymapp.gym.model.function.GymsToModelFunction;
import com.example.gymapp.gym.model.function.ModelToGymFunction;
import com.example.gymapp.member.model.function.MemberToEditModelFunction;
import com.example.gymapp.member.model.function.MemberToModelFunction;
import com.example.gymapp.member.model.function.ModelToMemberFunction;
import com.example.gymapp.member.model.function.UpdateMemberWithModelFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelFunctionFactory {

    public GymToModelFunction gymToModel() {
        return new GymToModelFunction();
    }

    public GymsToModelFunction gymsToModel() {
        return new GymsToModelFunction();
    }

    public GymToEditModelFunction gymToEditModel() {
        return new GymToEditModelFunction();
    }

    public ModelToGymFunction modelToGym() {
        return new ModelToGymFunction();
    }

    public MemberToModelFunction memberToModel() {
        return new MemberToModelFunction();
    }

    public MemberToEditModelFunction memberToEditModel() {
        return new MemberToEditModelFunction();
    }

    public ModelToMemberFunction modelToMember() {
        return new ModelToMemberFunction();
    }

    public UpdateMemberWithModelFunction updateMember() {
        return new UpdateMemberWithModelFunction();
    }
}
