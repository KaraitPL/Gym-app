package com.example.gymapp.configuration.listener;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.entity.TrainerGender;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {
    private MemberService memberService;

    private TrainerService trainerService;

    private GymService gymService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        memberService = (MemberService) event.getServletContext().getAttribute("memberService");
        trainerService = (TrainerService) event.getServletContext().getAttribute("trainerService");
        gymService = (GymService) event.getServletContext().getAttribute("gymService");
        init();
    }

    @SneakyThrows
    private void init() {
        Trainer trainer1 = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .login("trainer1Login")
                .name("trainer1Name")
                .birthDate(LocalDate.of(2002, 1, 1))
                .password("trainer1Haslo")
                .genders(List.of(TrainerGender.MALE, TrainerGender.FEMALE))
                .build();

        Trainer trainer2 = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .login("trainer2Login")
                .name("trainer2Name")
                .birthDate(LocalDate.of(2002, 1, 2))
                .password("trainer2Haslo")
                .genders(List.of(TrainerGender.MALE))
                .build();

        Trainer trainer3 = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .login("trainer3Login")
                .name("trainer3Name")
                .birthDate(LocalDate.of(2002, 1, 3))
                .password("trainer3Haslo")
                .genders(List.of(TrainerGender.FEMALE))
                .build();

        Trainer trainer4 = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                .login("trainer4Login")
                .name("trainer4Name")
                .birthDate(LocalDate.of(2002, 1, 4))
                .password("trainer4Haslo")
                .genders(List.of(TrainerGender.FEMALE))
                .build();

        trainerService.create(trainer1);
        trainerService.create(trainer2);
        trainerService.create(trainer3);
        trainerService.create(trainer4);




        Gym gym1 = Gym.builder()
                .id(UUID.fromString("00000000-0000-0000-0001-000000000001"))
                .name("gym1Name")
                .numberOfEquipment(1)
                .build();

        Gym gym2 = Gym.builder()
                .id(UUID.fromString("00000000-0000-0000-0001-000000000002"))
                .name("gym2Name")
                .numberOfEquipment(2)
                .build();

        Gym gym3 = Gym.builder()
                .id(UUID.fromString("00000000-0000-0000-0001-000000000003"))
                .name("gym3Name")
                .numberOfEquipment(3)
                .build();

        Gym gym4 = Gym.builder()
                .id(UUID.fromString("00000000-0000-0000-0001-000000000004"))
                .name("gym4Name")
                .numberOfEquipment(4)
                .build();

        gymService.create(gym1);
        gymService.create(gym2);
        gymService.create(gym3);
        gymService.create(gym4);

        Member member1 = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000001"))
                .name("member1")
                .age(1)
                .gym(gym1)
                .trainer(trainer1)
                .build();

        Member member2 = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000002"))
                .name("member2")
                .age(2)
                .gym(gym2)
                .trainer(trainer2)
                .build();

        Member member3 = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000003"))
                .name("member3")
                .age(3)
                .gym(gym3)
                .trainer(trainer3)
                .build();

        Member member4 = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000004"))
                .name("member4")
                .age(4)
                .gym(gym4)
                .trainer(trainer4)
                .build();

        memberService.create(member1);
        memberService.create(member2);
        memberService.create(member3);
        memberService.create(member4);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}

