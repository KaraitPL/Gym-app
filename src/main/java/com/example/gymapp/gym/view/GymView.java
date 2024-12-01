package com.example.gymapp.gym.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.service.MemberService;
import jakarta.ejb.EJB;
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
    private GymService gymService;

    private final ModelFunctionFactory factory;

    private MemberService memberService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private GymModel gym;

    @Inject
    public GymView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
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

    public String deleteMember(UUID trainerId) {
        memberService.delete(trainerId);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
