package com.example.gymapp.configuration.listener;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.entity.GymType;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.entity.Trainer;
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


    @Override
    public void contextInitialized(ServletContextEvent event) {
        memberService = (MemberService) event.getServletContext().getAttribute("memberService");
        trainerService = (TrainerService) event.getServletContext().getAttribute("trainerService");
        init();
    }

    @SneakyThrows
    private void init() {
        Trainer trainerArnold = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("Arnold Schwarzenegger")
                .yearsOfTraining(60)
                .birthDate(LocalDate.of(1947, 7, 30))
                .build();

        Trainer trainerRonnie = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .name("Ronnie Coleman")
                .yearsOfTraining(30)
                .birthDate(LocalDate.of(1964, 5, 13))
                .build();

        Trainer trainerZyzz = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .name("Zyzz")
                .yearsOfTraining(8)
                .birthDate(LocalDate.of(1989, 3, 24))
                .build();

        Trainer trainerDavid = Trainer.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                .name("David Laid")
                .yearsOfTraining(6)
                .birthDate(LocalDate.of(1998, 1, 29))
                .build();

        trainerService.create(trainerArnold);
        trainerService.create(trainerRonnie);
        trainerService.create(trainerZyzz);
        trainerService.create(trainerDavid);


        Member memberOskar = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000001"))
                .name("Oskar")
                .benchPressMax(110)
                .trainer(trainerArnold)
                .build();

        Member memberMarcin = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000002"))
                .name("Marcin")
                .benchPressMax(80)
                .trainer(trainerRonnie)
                .build();

        Member memberAntoni = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000003"))
                .name("Antoni")
                .benchPressMax(100)
                .trainer(trainerZyzz)
                .build();

        Member memberIgnacy = Member.builder()
                .id(UUID.fromString("00000000-0000-0000-0002-000000000004"))
                .name("Ignacy")
                .benchPressMax(100)
                .trainer(trainerDavid)
                .build();

        memberService.create(memberOskar);
        memberService.create(memberMarcin);
        memberService.create(memberAntoni);
        memberService.create(memberIgnacy);
    }

}

