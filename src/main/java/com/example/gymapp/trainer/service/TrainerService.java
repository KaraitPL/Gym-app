package com.example.gymapp.trainer.service;

import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.entity.TrainerRoles;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class TrainerService {

    private final TrainerRepository repository;

    private final MemberService memberService;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public TrainerService(TrainerRepository repository, MemberService memberService, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.memberService = memberService;
        this.passwordHash = passwordHash;
    }

    public Optional<Trainer> find(UUID id){
        return repository.find(id);
    }

    public Optional<Trainer> find(String name){
        return repository.findByName(name);
    }

    public List<Trainer> findAll(){
        return repository.findAll();
    }

    @PermitAll
    public void create(Trainer trainer) {
        trainer.setPassword(passwordHash.generate(trainer.getPassword().toCharArray()));
        repository.create(trainer);
    }

    public void update(Trainer trainer){
        repository.update(trainer);
    }

    public void delete(UUID id) {
        Trainer trainer = repository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Member>> membersToDelete = memberService.findAllByTrainer(id);
        membersToDelete.ifPresent(members -> members.forEach(member -> {
            memberService.delete(member.getId());
        }));
        repository.delete(trainer);

    }

    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws NotAllowedException {
        repository.find(id).ifPresent(trainer -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new NotAllowedException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

/*    public void deleteAvatar(UUID id) {
        repository.find(id).ifPresent(trainer -> {
                trainer.setAvatar(null);
                repository.update(trainer);
        });
    }*/

    public void updateAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        repository.find(id).ifPresent(trainer -> {
            try {
                Path existingPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(existingPath)) {
                    Files.copy(avatar, existingPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NotFoundException("Trainer avatar not found, to create avatar use PUT method");
                }
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }



}

