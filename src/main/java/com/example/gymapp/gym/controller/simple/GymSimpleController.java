package com.example.gymapp.gym.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

@Path("")
public class GymSimpleController implements GymController {
    private final GymService gymService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public GymSimpleController(DtoFunctionFactory factory, GymService gymService, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.factory = factory;
        this.gymService = gymService;
        this.uriInfo = uriInfo;
    }
    @Override
    public GetGymResponse getGym(UUID id) {
        return gymService.find(id).
                map(factory.gymToResponse())
                .orElseThrow(() -> new NotFoundException("Gym not found"));
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
            throw new BadRequestException(ex.getMessage());
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
