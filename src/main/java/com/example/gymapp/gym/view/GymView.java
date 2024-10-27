package com.example.gymapp.gym.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.service.MemberService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class GymView implements Serializable {
    private final GymService gymService;

    private final ModelFunctionFactory factory;

    private final MemberService memberService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private GymModel gym;

    @Inject
    public GymView(GymService gymService, ModelFunctionFactory factory, MemberService memberService) {
        this.gymService = gymService;
        this.factory = factory;
        this.memberService = memberService;
    }

    public void init() throws IOException {
        Optional<Gym> gym = gymService.find(id);
        if (gym.isPresent()) {
            this.gym = factory.gymToModel().apply(gym.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Gym not found!!!");
        }
    }

    public String deleteMember(UUID userId) {
        memberService.delete(userId);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
