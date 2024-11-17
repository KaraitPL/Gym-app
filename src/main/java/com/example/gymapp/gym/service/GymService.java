package com.example.gymapp.gym.service;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
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

    public Optional<Gym> find(UUID id) { return repository.find(id); }

    public Optional<Gym> find(String name) { return repository.findByName(name); }

    public List<Gym> findAll() { return repository.findAll(); }

    public void create(Gym gym) { repository.create(gym); }

    public void delete(UUID id)
    {
        Gym gym = repository.find(id).orElseThrow(() -> new NotFoundException("Gym not found"));
        Optional<List<Member>> membersToDelete = memberService.findAllByGym(id);
        membersToDelete.ifPresent(members -> members.forEach(member -> {
            memberService.delete(member.getId());
        }));

        membersToDelete.ifPresent(units -> {

        });

        repository.delete(gym); }

    public void update(Gym gym) { repository.update(gym); }

}
