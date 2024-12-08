package com.example.gymapp.trainer.model.converter;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.model.TrainerModel;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(value = "trainerConverter", forClass = TrainerModel.class, managed = true)
public class TrainerModelConverter implements Converter<TrainerModel> {
    private final TrainerService service;
    private final ModelFunctionFactory factory;
    @Inject
    public TrainerModelConverter(TrainerService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public TrainerModel getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("TrainerModelConverter.getAsObject");
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Trainer> trainer = service.find(UUID.fromString(value));
        System.out.println("TrainerModelConverter.getAsObject");
        return trainer.map(factory.trainerToModel()).orElse(null);
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, TrainerModel value) {
        System.out.println("TrainerModelConverter.getAsString");
        return value == null ? "" : value.getId().toString();
    }
}
