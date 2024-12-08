package com.example.gymapp.gym.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.model.GymsModel;
import com.example.gymapp.gym.service.GymService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class GymList {
    private GymService gymService;

    private final ModelFunctionFactory factory;

    private GymsModel gyms;


    @Inject
    public GymList(ModelFunctionFactory factory) {
        this.factory = factory;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
    }

    @EJB
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    public GymsModel getGyms() {
        if (gyms == null) {
            gyms = factory.gymsToModel().apply(gymService.findAll());
        }
        return gyms;
    }

    public String deleteAction(GymsModel.Gym gym) {
        gymService.delete(gym.getId());
        return "gym_list?faces-redirect=true";
    }

}
