package project.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.demo.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findById(int id);
}
