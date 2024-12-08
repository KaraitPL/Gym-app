package com.example.gymapp.gym.service;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.TrainerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class GymService {
    private final GymRepository repository;
    private final MemberService memberService;

    @Inject
    public GymService(GymRepository gymRepository, MemberService memberService)
    {
        this.repository = gymRepository;
        this.memberService = memberService;
    }

    @RolesAllowed(TrainerRoles.USER)
    public Optional<Gym> find(UUID id) { return repository.find(id); }


    @RolesAllowed(TrainerRoles.USER)
    public List<Gym> findAll() { return repository.findAll(); }

    @RolesAllowed(TrainerRoles.ADMIN)
    public void create(Gym gym) { repository.create(gym); }

    @RolesAllowed(TrainerRoles.ADMIN)
    public void delete(UUID id)
    {
        Gym gym = repository.find(id).orElseThrow(() -> new NotFoundException("Gym not found"));
        Optional<List<Member>> membersToDelete = memberService.findAllByGym(id);
        membersToDelete.ifPresent(members -> members.forEach(member -> {
            memberService.delete(member.getId());
        }));

        repository.delete(gym); }

    @RolesAllowed(TrainerRoles.USER)
    public void update(Gym gym) { repository.update(gym); }

}
