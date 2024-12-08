package com.example.gymapp.configuration;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.entity.GymType;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.entity.TrainerRoles;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final TrainerRepository trainerRepository;

    private final Pbkdf2PasswordHash passwordHash;




    @Inject
    public InitializeAdminService(
            TrainerRepository trainerRepository, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.trainerRepository = trainerRepository;
        this.passwordHash = passwordHash;

    }


    @PostConstruct
    @SneakyThrows
    private void init() {
        if (trainerRepository.findByName("admin-service").isEmpty()) {

            Trainer admin = Trainer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                    .name("admin-service")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(TrainerRoles.ADMIN, TrainerRoles.USER))
                    .build();

            trainerRepository.create(admin);






        }
    }

}
