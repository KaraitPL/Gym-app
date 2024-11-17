package com.example.gymapp.gym.controller.api;

import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.dto.PatchGymRequest;
import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.member.dto.GetMembersResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

public interface GymController {

    @GET
    @Path("/gyms/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetGymResponse getGym(@PathParam("id") UUID id);

    @GET
    @Path("/gyms")
    @Produces(MediaType.APPLICATION_JSON)
    GetGymsResponse getGyms();

    @PUT
    @Path("/gyms/{id}")
    void putGym(@PathParam("id") UUID id, PutGymRequest request);

    @PATCH
    @Path("/gyms/{id}")
    void patchGym(@PathParam("id") UUID id, PatchGymRequest request);

    @DELETE
    @Path("/gyms/{id}")
    void deleteGym(@PathParam("id") UUID id);



}
