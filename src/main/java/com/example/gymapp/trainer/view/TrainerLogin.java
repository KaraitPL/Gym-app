package com.example.gymapp.trainer.view;

import com.example.gymapp.trainer.service.TrainerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@RequestScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class TrainerLogin {
    private final HttpServletRequest request;

    private final SecurityContext securityContext;

    private final FacesContext facesContext;

    private final TrainerService trainerService;

    @Inject
    public TrainerLogin(HttpServletRequest request, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext, FacesContext facesContext, TrainerService trainerService) {
        this.request = request;
        this.securityContext = securityContext;
        this.facesContext = facesContext;
        this.trainerService = trainerService;
    }
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String password;
    @SneakyThrows
    public void loginAction() {
        Credential credential = new UsernamePasswordCredential(name, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(request, extractResponseFromFacesContext(),
                withParams().credential(credential));
        if (status == AuthenticationStatus.SUCCESS) {
            System.out.println("SUCCESS");
        } else if (status == AuthenticationStatus.SEND_CONTINUE) {
            System.out.println("SEND_CONTINUE");
        } else if (status == AuthenticationStatus.SEND_FAILURE) {
            System.out.println("Authentication failed");
        }
        facesContext.responseComplete();
    }
    private HttpServletResponse extractResponseFromFacesContext() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }
}