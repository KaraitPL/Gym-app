package com.example.gymapp.configuration.listener;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.gym.controller.simple.GymSimpleController;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.controller.simple.MemberSimpleController;
import com.example.gymapp.member.service.MemberService;
import com.example.gymapp.trainer.controller.simple.TrainerSimpleController;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        MemberService memberService = (MemberService) event.getServletContext().getAttribute("memberService");
        GymService gymService = (GymService) event.getServletContext().getAttribute("gymService");
        TrainerService trainerService = (TrainerService) event.getServletContext().getAttribute("trainerService");

        event.getServletContext().setAttribute("memberController", new MemberSimpleController(
                memberService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("gymController", new GymSimpleController(
                gymService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("trainerController", new TrainerSimpleController(
                trainerService,
                new DtoFunctionFactory()
        ));
    }
}

