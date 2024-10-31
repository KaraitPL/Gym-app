package com.example.gymapp.member.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;


import java.util.UUID;

@Path("")
public class MemberSimpleController implements MemberController {

    private final MemberService service;

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public MemberSimpleController(MemberService service, DtoFunctionFactory factory,  @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetMembersResponse getTrainerMembers(UUID id) {
        return service.findAllByTrainer(id)
                .map(factory.membersToResponse())
                .orElseThrow(() -> new NotFoundException("Trainer not found"));
    }

    @Override
    public GetMembersResponse getGymMembers(UUID id) {
        return service.findAllByGym(id)
                .map(factory.membersToResponse())
                .orElseThrow(() -> new NotFoundException("Gym not found"));
    }

    @Override
    public GetMemberResponse getMember(UUID id) {
        return service.find(id)
                .map(factory.memberToResponse())
                .orElseThrow(() -> new NotFoundException("Member not found"));
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

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MemberController.class, "getUnit")
                    .build(id)
                    .toString());
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Member already exists");
        }
    }

    @Override
    public void patchMember(UUID id, PatchMemberRequest request) {
        service.find(id).ifPresentOrElse(entity -> service.update(factory.updateMember().apply(entity, request), entity.getGym().getId()), () -> {
            throw new NotFoundException("Member not found");
        });
    }

    @Override
    public void deleteMember(UUID id) {
        service.find(id).ifPresentOrElse(entity -> service.delete(id), () -> {
            throw new NotFoundException("Member not found");
        });
    }


}

