package ru.slorimer.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.slorimer.RestApp.Services.SensorService;
import ru.slorimer.RestApp.models.Sensor;
import ru.slorimer.RestApp.dto.SensorDTO;
import ru.slorimer.RestApp.util.ProjectErrorResponse;
import ru.slorimer.RestApp.util.ProjectException;
import ru.slorimer.RestApp.util.SensorValidation;

import static ru.slorimer.RestApp.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private SensorService sensorService;
    private SensorValidation sensorValidation;
    private ModelMapper modelMapper;

    public SensorController(SensorService sensorService, SensorValidation sensorValidation, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidation = sensorValidation;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> SensorRegistration(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensor = convertToSensor(sensorDTO);

        sensorValidation.validate(sensor, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ProjectErrorResponse> handlerException(ProjectException e){
        ProjectErrorResponse response = new ProjectErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
