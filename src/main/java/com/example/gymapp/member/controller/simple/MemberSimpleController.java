package com.example.gymapp.member.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.GetMembersResponse;
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



}

