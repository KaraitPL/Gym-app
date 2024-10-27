package com.example.gymapp.member.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberModel;
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
public class MemberView implements Serializable {
    private final MemberService memberService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MemberModel member;

    @Inject
    public MemberView(MemberService memberService, ModelFunctionFactory factory) {
        this.memberService = memberService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Member> member = memberService.find(id);
        if (member.isPresent()) {
            this.member = factory.memberToModel().apply(member.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Member not found!!!");
        }
    }


}
