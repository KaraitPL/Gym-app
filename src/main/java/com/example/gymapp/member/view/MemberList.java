package com.example.gymapp.member.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.member.model.MembersModel;
import com.example.gymapp.member.service.MemberService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class MemberList {
    private MemberService memberService;

    private MembersModel members;

    private final ModelFunctionFactory factory;

    @Inject
    public MemberList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    public MembersModel getMembers() {
        if(members == null) {
            members = factory.membersToModel().apply(memberService.findAllForCallerPrincipal());
        }
        return members;
    }

    @EJB
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public String deleteAction(MembersModel.Member member){
        memberService.delete(member.getId());
        return "member_list?faces-redirect=true";
    }
}
