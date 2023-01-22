package ru.slorimer.RestApp.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.slorimer.RestApp.Services.SensorService;
import ru.slorimer.RestApp.models.Sensor;
@Component
public class SensorValidation implements Validator {

    private SensorService sensorService;

    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "this name already exists");
        }
    }
}
