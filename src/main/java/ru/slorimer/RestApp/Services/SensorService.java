package ru.slorimer.RestApp.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slorimer.RestApp.Repositories.SensorRepository;
import ru.slorimer.RestApp.models.Sensor;

import java.util.Optional;

@Service
public class SensorService {
    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
