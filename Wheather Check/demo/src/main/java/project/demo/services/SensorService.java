package project.demo.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.models.Sensor;
import project.demo.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> all(){
        return sensorRepository.findAll();
    }

    public Sensor show(int id){
        return sensorRepository.findById(id);
    }
    @Transactional
    public void save(Sensor sensor, JsonNode node) {
        fix(sensor);
        sensor.setCity(String.valueOf(node.get("location").get("name")));
        sensor.setWeather_type(String.valueOf(node.get("current").get("condition").get("text")));

        sensorRepository.save(sensor);
    }

    private void fix(Sensor sensor) {
        sensor.setCreated_at(new Date());
    }
}
