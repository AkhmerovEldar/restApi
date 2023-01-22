package ru.slorimer.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.slorimer.RestApp.Services.MeasurementsService;
import ru.slorimer.RestApp.dto.MeasurementResponse;
import ru.slorimer.RestApp.dto.MeasurementsDTO;
import ru.slorimer.RestApp.models.Measurements;
import ru.slorimer.RestApp.util.MeasurementsValidation;
import ru.slorimer.RestApp.util.ProjectErrorResponse;
import ru.slorimer.RestApp.util.ProjectException;

import java.util.stream.Collectors;

import static ru.slorimer.RestApp.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private MeasurementsValidation measurementsValidation;

    private final MeasurementsService measurementsService;

    public MeasurementsController(ModelMapper modelMapper, MeasurementsValidation measurementsValidation, MeasurementsService measurementsService) {
        this.modelMapper = modelMapper;
        this.measurementsValidation = measurementsValidation;
        this.measurementsService = measurementsService;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@Valid @RequestBody MeasurementsDTO measurementsDTO,
                                                     BindingResult bindingResult){
        Measurements measurements = convertToMeasurement(measurementsDTO);
        measurementsValidation.validate(measurements, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        measurementsService.save(measurements);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementResponse getAllMeasurements(){
        return new MeasurementResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount(){
        return measurementsService.findAll().stream().filter(Measurements::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<ProjectErrorResponse> ExceptionHandler(ProjectException e){
        ProjectErrorResponse response = new ProjectErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    public MeasurementsDTO convertToMeasurementDTO(Measurements measurements){
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }

    public Measurements convertToMeasurement(MeasurementsDTO measurementsDTO){
        return modelMapper.map(measurementsDTO, Measurements.class);
    }
}
