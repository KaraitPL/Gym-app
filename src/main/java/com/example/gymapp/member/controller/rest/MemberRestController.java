package com.example.gymapp.member.controller.rest;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.TrainerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;


import java.util.UUID;
import java.util.logging.Level;


@Path("")
@Log
public class MemberRestController implements MemberController {

    private MemberService service;

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public MemberRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
        this.service = memberService;
    }

    @Override
    public GetMembersResponse getTrainerMembers(UUID id) {
        return service.findAllByTrainer(id)
                .map(factory.membersToResponse())
                .orElseThrow(() -> new NotFoundException("Trainer not found"));
    }

    @RolesAllowed(TrainerRoles.ADMIN)
    @Override
    public GetMembersResponse getGymMembers(UUID id) {
        return service.findAllByGym(id)
                .map(factory.membersToResponse())
                .orElseThrow(() -> new NotFoundException("Gym not found"));
    }

    @RolesAllowed(TrainerRoles.USER)
    @Override
    public GetMemberResponse getGymMember(UUID gymId, UUID memberId) {
        try {
            return service.findForCallerPrincipal(gymId, memberId)
                    .map(factory.memberToResponse())
                    .orElseThrow(() -> new NotFoundException("Member not found in the specified gym"));
        } catch (EJBAccessException e) {
            throw new ForbiddenException("Forbidden access!");
        }
    }

    @Override
    public GetMemberResponse getMember(UUID id) {
        return service.find(id)
                .map(factory.memberToResponse())
                .orElseThrow(() -> new NotFoundException("Member not found"));
    }

    @RolesAllowed(TrainerRoles.ADMIN)
    @Override
    public GetMembersResponse getMembers() {
        return factory.membersToResponse().apply(service.findAll());
    }


    @Override
    public void putGymMember(UUID gymId, UUID memberId, PutMemberRequest request) {
        try {
            request.setGym(gymId);
            Member member = factory.requestToMember().apply(memberId, request);
            service.create(member, request.getTrainer(), gymId);

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MemberController.class, "getMember")
                    .build(memberId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Member already exists, to update member use PATCH method");
            }
            throw ex;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public void patchGymMember(UUID gymId, UUID memberId, PatchMemberRequest request) {
        service.findByGymAndMember(gymId, memberId).ifPresentOrElse(
                entity -> service.update(factory.updateMember().apply(entity, request), gymId),
                () -> {
                    throw new NotFoundException("Member not found");
                });
    }

    @Override
    public void deleteGymMember(UUID gymId, UUID memberId) {
        service.findByGymAndMember(gymId, memberId).ifPresentOrElse(
                entity -> service.delete(memberId),
                () -> {
                    throw new NotFoundException("Member not found");
                });
    }



}

