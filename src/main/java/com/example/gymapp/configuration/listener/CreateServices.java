package com.example.gymapp.configuration.listener;

import com.example.gymapp.crypto.component.Pbkdf2PasswordHash;
import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.gym.repository.memory.GymInMemoryRepository;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.member.repository.memory.MemberInMemoryRepository;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import com.example.gymapp.trainer.repository.memory.TrainerInMemoryRepository;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        TrainerRepository trainerRepository = new TrainerInMemoryRepository(dataSource);
        GymRepository gymRepository = new GymInMemoryRepository(dataSource);
        MemberRepository memberRepository = new MemberInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("trainerService", new TrainerService(trainerRepository, new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("memberService", new MemberService(memberRepository, gymRepository, trainerRepository));
        event.getServletContext().setAttribute("gymService", new GymService(gymRepository));
    }

}

