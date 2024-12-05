package com.example.gymapp.gym.controller.rest;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.gym.controller.api.GymController;
import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.dto.PatchGymRequest;
import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.trainer.entity.TrainerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ejb.EJB;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(TrainerRoles.USER)
public class GymRestController implements GymController {
    private GymService gymService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    @Inject
    public GymRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
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

    @RolesAllowed("admin")
    @Override
    public void putGym(UUID id, PutGymRequest request) {
        try {
            gymService.create(factory.requestToGym().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(GymController.class, "getGym")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Gym already exists");
            }
            throw ex;
        }
    }

    @Override
    public void patchGym(UUID id, PatchGymRequest request) {
        gymService.find(id)
                .ifPresentOrElse(entity -> gymService.update(factory.updateGym().apply(entity, request)), () -> {
                    throw new NotFoundException("Gym not found");
                });
    }

    @RolesAllowed("admin")
    @Override
    public void deleteGym(UUID id) {
        gymService.find(id).ifPresentOrElse(entity -> gymService.delete(id), () -> {
            throw new NotFoundException("Gym not found");
        });
    }
}
