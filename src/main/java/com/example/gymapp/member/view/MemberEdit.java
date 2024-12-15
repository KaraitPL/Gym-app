package com.example.gymapp.member.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberEditModel;
import com.example.gymapp.member.service.MemberService;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MemberEdit implements Serializable {

    private MemberService memberService;

    private final ModelFunctionFactory factory;

    private GymService gymService;

    private final FacesContext facesContext;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MemberEditModel member;

    @Setter
    @Getter
    private UUID newGym;

    @Setter
    @Getter
    private List<GymModel> gyms;

    @Inject
    public MemberEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    public void init() throws IOException {
        Optional<Member> member = memberService.find(id);
        if (member.isPresent()) {
            this.member = factory.memberToEditModel().apply(member.get());
            this.newGym = this.member.getGym().getId();
            this.gyms = gymService.findAll().stream().map(factory.gymToModel()).toList();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Member not found");
        }
    }

    public String saveAction() throws IOException {
        if (member.getGym() == null || member.getName() == null) {
            return null;
        }
        try {
            memberService.update(factory.updateMember().apply(memberService.find(id).orElseThrow(), member), newGym);
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (TransactionalException ex) {
            System.out.println("Blad");
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }

}
