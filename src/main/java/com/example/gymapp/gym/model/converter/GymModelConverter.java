package com.example.gymapp.gym.model.converter;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.service.GymService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = GymModel.class, managed = true)
public class GymModelConverter implements Converter<GymModel> {
    private final GymService gymService;

    private final ModelFunctionFactory factory;

    @Inject
    public GymModelConverter(GymService gymService, ModelFunctionFactory factory) {
        this.gymService = gymService;
        this.factory = factory;
    }

    @Override
    public GymModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Gym> gym = gymService.find(UUID.fromString(value));
        return gym.map(factory.gymToModel()).orElse(null);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, GymModel value) {
        return value == null ? "" : value.getId().toString();


    }
}
