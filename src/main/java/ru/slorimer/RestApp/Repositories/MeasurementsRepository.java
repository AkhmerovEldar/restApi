package ru.slorimer.RestApp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slorimer.RestApp.models.Measurements;
import ru.slorimer.RestApp.models.Sensor;

import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
}
