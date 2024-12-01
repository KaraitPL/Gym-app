package com.example.gymapp.member.controller.api;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
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
    @Path("/gyms/{gymId}/members/{memberId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMemberResponse getGymMember(@PathParam("gymId") UUID gymId, @PathParam("memberId") UUID memberId);

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
    void putGymMember(@PathParam("gymId") UUID gymId, @PathParam("id") UUID memberId, PutMemberRequest request);

    @PATCH
    @Path("gyms/{gymId}/members/{id}")
    void patchGymMember(@PathParam("gymId") UUID gymId, @PathParam("id") UUID id, PatchMemberRequest request);

    @DELETE
    @Path("gyms/{gymId}/members/{id}")
    void deleteGymMember(@PathParam("gymId") UUID gymId, @PathParam("id") UUID id);
}
