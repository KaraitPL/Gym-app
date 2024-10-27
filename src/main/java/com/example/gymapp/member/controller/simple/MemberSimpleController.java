package com.example.gymapp.member.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;


import java.util.UUID;

@RequestScoped
public class MemberSimpleController implements MemberController {

    private final MemberService service;
    private final GymService gymService;

    private final DtoFunctionFactory factory;

    @Inject
    public MemberSimpleController(MemberService service, DtoFunctionFactory factory, GymService gymService) {
        this.service = service;
        this.factory = factory;
        this.gymService = gymService;
    }

    @Override
    public GetMembersResponse getTrainerMembers(UUID id) {
        return service.findAllByTrainer(id)
                .map(factory.membersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMembersResponse getGymMembers(UUID id) {
        return service.findAllByGym(id)
                .map(factory.membersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMemberResponse getMember(UUID id) {
        return service.find(id)
                .map(factory.memberToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMembersResponse getMembers() {
        return factory.membersToResponse().apply(service.findAll());
    }

    @Override
    public void putMember(UUID id, PutMemberRequest request) {
        try{
            Member member = factory.requestToMember().apply(id, request);
            service.create(member, request.getTrainer(), request.getGym());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchMember(UUID id, PatchMemberRequest request) {
        service.find(id).ifPresentOrElse(entity -> service.update(factory.updateMember().apply(entity, request), entity.getGym().getId()), () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void deleteMember(UUID id) {
        service.find(id).ifPresentOrElse(entity -> service.delete(id), () -> {
            throw new NotFoundException();
        });
    }


}

