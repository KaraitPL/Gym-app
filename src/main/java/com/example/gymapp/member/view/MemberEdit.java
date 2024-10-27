package com.example.gymapp.member.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberEditModel;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MemberEdit implements Serializable {

    private final MemberService memberService;

    private final ModelFunctionFactory factory;

    private final GymService gymService;

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
    public MemberEdit(MemberService memberService, ModelFunctionFactory factory, GymService gymService) {
        this.memberService = memberService;
        this.factory = factory;
        this.gymService = gymService;
    }

    public void init() throws IOException {
        Optional<Member> member = memberService.find(id);
        if (member.isPresent()) {
            this.member = factory.memberToEditModel().apply(member.get());
            this.newGym = this.member.getGym().getId();
            this.gyms = gymService.findAll().stream().map(factory.gymToModel()).toList();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Member not found!!!");
        }
    }

    public String saveAction() {
        if (member.getGym() == null || member.getName() == null) {
            return null;
        }
        memberService.update(factory.updateMember().apply(memberService.find(id).orElseThrow(), member), newGym);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
