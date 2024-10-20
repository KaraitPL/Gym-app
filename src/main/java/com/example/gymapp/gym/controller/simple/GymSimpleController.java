package com.example.gymapp.gym.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.gym.controller.api.GymController;
import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.dto.PatchGymRequest;
import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.service.MemberService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class GymSimpleController implements GymController {
    private final GymService gymService;
    private final DtoFunctionFactory factory;

    @Inject
    public GymSimpleController(DtoFunctionFactory factory, GymService gymService){
        this.factory = factory;
        this.gymService = gymService;
    }
    @Override
    public GetGymResponse getGym(UUID id) {
        return gymService.find(id).
                map(factory.gymToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetGymsResponse getGyms() {
        return factory.gymsToResponse().apply(gymService.findAll());
    }

    @Override
    public void putGym(UUID id, PutGymRequest request) {
        try{
            gymService.create(factory.requestToGym().apply(id, request));
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchGym(UUID id, PatchGymRequest request) {
        gymService.find(id).ifPresentOrElse(entity -> gymService.update(factory.updateGym().apply(entity, request)), () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void deleteGym(UUID id) {
        gymService.find(id).ifPresentOrElse(
                entity -> gymService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
