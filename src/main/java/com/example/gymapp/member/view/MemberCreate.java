package com.example.gymapp.member.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.model.MemberCreateModel;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class MemberCreate implements Serializable {

    private MemberService memberService;

    private final ModelFunctionFactory factory;

    private TrainerService trainerService;

    private GymService gymService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MemberCreateModel member;

    @Setter
    @Getter
    private List<GymModel> gyms;

    private static final UUID TEMP_TRAINER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Inject
    public MemberCreate(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    public void init() throws IOException {
        Trainer tempTrainer = this.trainerService.find(TEMP_TRAINER_ID).get();
        this.member = MemberCreateModel.builder().id(UUID.randomUUID()).trainer(tempTrainer).build();
        this.gyms = gymService.findAll().stream().map(factory.gymToModel()).toList();
    }

    public String saveAction() {
        if (member.getGym() == null || member.getName() == null) {
            return null;
        }
        memberService.create(factory.modelToMember().apply(member), TEMP_TRAINER_ID, member.getGym().getId());
        return "/gym/gym_view.xhtml?faces-redirect=true&id=" + member.getGym().getId();

    }

}
