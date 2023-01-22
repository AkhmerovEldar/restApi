package ru.slorimer.RestApp.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slorimer.RestApp.Repositories.MeasurementsRepository;
import ru.slorimer.RestApp.models.Measurements;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementsService {
    private MeasurementsRepository measurementsRepository;
    private SensorService sensorService;


    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    public List<Measurements> findAll(){
        return measurementsRepository.findAll();
    }
    @Transactional
    public void save(Measurements measurements){
        enrichMeasurements(measurements);
        measurementsRepository.save(measurements);
    }

    private void enrichMeasurements(Measurements measurements){
        measurements.setSensor(sensorService.findByName(measurements.getSensor().getName()).get());

        measurements.setMeasurementDateTime(LocalDateTime.now());
    }
}
