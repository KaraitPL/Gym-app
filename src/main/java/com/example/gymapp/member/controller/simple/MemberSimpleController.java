package com.example.gymapp.member.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.member.service.MemberService;


import java.util.UUID;

public class MemberSimpleController implements MemberController {

    private final MemberService service;

    private final DtoFunctionFactory factory;

    public MemberSimpleController(MemberService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetMembersResponse getTrainerMembers(UUID id) {
        return service.findAllByTrainer(id)
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
            service.create(factory.requestToMember().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchMember(UUID id, PatchMemberRequest request) {
        service.find(id).ifPresentOrElse(entity -> service.update(factory.updateMember().apply(entity, request)), () -> {
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

