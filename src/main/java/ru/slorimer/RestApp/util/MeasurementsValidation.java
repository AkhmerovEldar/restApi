package ru.slorimer.RestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.slorimer.RestApp.Services.MeasurementsService;
import ru.slorimer.RestApp.Services.SensorService;
import ru.slorimer.RestApp.models.Measurements;
import ru.slorimer.RestApp.models.Sensor;
@Component
public class MeasurementsValidation  implements Validator {
    private SensorService sensorService;

    public MeasurementsValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurements.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurements measurements = (Measurements) target;

        if(sensorService.findByName(measurements.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "", "not found sensor");
    }
}
