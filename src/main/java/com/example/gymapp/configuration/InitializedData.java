package com.example.gymapp.configuration;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.entity.GymType;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.entity.TrainerRoles;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletContextListener;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({TrainerRoles.ADMIN, TrainerRoles.USER})
@RunAs(TrainerRoles.ADMIN)
public class InitializedData implements ServletContextListener {

    private TrainerService trainerService;
    private MemberService memberService;
    private GymService gymService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        System.out.print("Siema 1");
        if (trainerService.find("Arnold Schwarzenegger").isEmpty()) {
            System.out.print("Siema 2");
            Trainer trainerArnold = Trainer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .name("Arnold Schwarzenegger")
                    .yearsOfTraining(60)
                    .password("arnold")
                    .members(Collections.emptyList())
                    .roles(List.of(TrainerRoles.ADMIN, TrainerRoles.USER))
                    .birthDate(LocalDate.of(1947, 7, 30))
                    .build();

            Trainer trainerRonnie = Trainer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .name("Ronnie Coleman")
                    .yearsOfTraining(30)
                    .password("arnold")
                    .members(Collections.emptyList())
                    .roles(List.of(TrainerRoles.USER))
                    .birthDate(LocalDate.of(1964, 5, 13))
                    .build();

            Trainer trainerZyzz = Trainer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .name("Zyzz")
                    .yearsOfTraining(8)
                    .password("arnold")
                    .members(Collections.emptyList())
                    .roles(List.of(TrainerRoles.USER))
                    .birthDate(LocalDate.of(1989, 3, 24))
                    .build();

            Trainer trainerDavid = Trainer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                    .name("David Laid")
                    .yearsOfTraining(6)
                    .password("arnold")
                    .members(Collections.emptyList())
                    .roles(List.of(TrainerRoles.USER))
                    .birthDate(LocalDate.of(1998, 1, 29))
                    .build();

            trainerService.create(trainerArnold);
            trainerService.create(trainerRonnie);
            trainerService.create(trainerZyzz);
            trainerService.create(trainerDavid);

            Gym goldGym = Gym.builder()
                    .id(UUID.fromString("00000000-0000-0000-0001-000000000001"))
                    .name("Gold's Gym")
                    .numberOfEquipment(532)
                    .gymType(GymType.NORMAL)
                    .members(Collections.emptyList())
                    .build();

            Gym muscleGym = Gym.builder()
                    .id(UUID.fromString("00000000-0000-0000-0001-000000000002"))
                    .name("Muscle Gym")
                    .numberOfEquipment(45)
                    .gymType(GymType.BEACH)
                    .members(Collections.emptyList())
                    .build();

            Gym streetWorkoutPark = Gym.builder()
                    .id(UUID.fromString("00000000-0000-0000-0001-000000000003"))
                    .name("Street Workout Park")
                    .numberOfEquipment(23)
                    .gymType(GymType.CALISTHENIC)
                    .members(Collections.emptyList())
                    .build();

            gymService.create(goldGym);
            gymService.create(muscleGym);
            gymService.create(streetWorkoutPark);

            Member memberOskar = Member.builder()
                    .id(UUID.fromString("00000000-0000-0000-0002-000000000001"))
                    .name("Oskar")
                    .gym(muscleGym)
                    .benchPressMax(110)
                    .trainer(trainerArnold)
                    .build();

            Member memberMarcin = Member.builder()
                    .id(UUID.fromString("00000000-0000-0000-0002-000000000002"))
                    .name("Marcin")
                    .gym(streetWorkoutPark)
                    .benchPressMax(80)
                    .trainer(trainerRonnie)
                    .build();

            Member memberAntoni = Member.builder()
                    .id(UUID.fromString("00000000-0000-0000-0002-000000000003"))
                    .name("Antoni")
                    .gym(goldGym)
                    .benchPressMax(100)
                    .trainer(trainerZyzz)
                    .build();

            Member memberIgnacy = Member.builder()
                    .id(UUID.fromString("00000000-0000-0000-0002-000000000004"))
                    .name("Ignacy")
                    .gym(muscleGym)
                    .benchPressMax(100)
                    .trainer(trainerDavid)
                    .build();

            memberService.create(memberOskar, trainerArnold.getId(), muscleGym.getId());
            memberService.create(memberMarcin, trainerRonnie.getId(), streetWorkoutPark.getId());
            memberService.create(memberAntoni, trainerZyzz.getId(), goldGym.getId());
            memberService.create(memberIgnacy, trainerDavid.getId(), muscleGym.getId());
        }
    }

    /*@SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }*/

}

