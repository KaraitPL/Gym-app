package com.example.gymapp.member.controller.api;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

public interface MemberController {
    @GET
    @Path("/trainers/{id}/members")
    @Produces(MediaType.APPLICATION_JSON)
    GetMembersResponse getTrainerMembers(@PathParam("id") UUID id);

    @GET
    @Path("/gyms/{id}/members")
    @Produces(MediaType.APPLICATION_JSON)
    GetMembersResponse getGymMembers(@PathParam("id") UUID id);

    @GET
    @Path("/members/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMemberResponse getMember(@PathParam("id") UUID id);

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    GetMembersResponse getMembers();

    @PUT
    @Path("gyms/{gymId}/members/{id}")
    void putMember(@PathParam("id") UUID id, PutMemberRequest request, @PathParam("gymId") UUID gymId);

    @PATCH
    @Path("gyms/{gymId}/members/{id}")
    void patchMember(@PathParam("id") UUID id, PatchMemberRequest request, @PathParam("gymId") UUID gymId);

    @DELETE
    @Path("/members/{id}")
    void deleteMember(@PathParam("id") UUID id);
}
