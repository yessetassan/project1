package project.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import project.demo.dto.SensorDTO;
import project.demo.models.Sensor;
import project.demo.services.SensorService;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    @Autowired
    public WeatherController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public String welcome(){
        return "weather/hello";
    }

    @GetMapping("/measurements")
    public String all(Model model){
        model.addAttribute("measurements", sensorService.all());
        return "weather/all";
    }

    @GetMapping("/measurements/{id}")
    public String all(@PathVariable("id") int id, Model model){
        model.addAttribute("measurement", sensorService.show(id));
        return "weather/show";
    }


    public SensorDTO sensorDTO = new SensorDTO();
    @GetMapping("/measurements/add")
    public String add(Model model){
        model.addAttribute("sensor_dto", sensorDTO);
        return "weather/add";
    }



    @PostMapping("/measurements/add")
    public String post( @ModelAttribute("sensor_dto")SensorDTO sensorDTO, Model model) {


        try {

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://api.weatherapi.com/v1/current.json?key=87146ced99e447d790f111459231307&q=" + sensorDTO.getCity() + "&aqi=no";
            ObjectMapper objectMapper = new ObjectMapper();
            String s = restTemplate.getForObject(url,String.class);
            JsonNode node = objectMapper.readTree(s);
            sensorDTO.setTemperature(Double.parseDouble(String.valueOf(node.get("current").get("temp_c"))));
            sensorService.save(convertToSensor(sensorDTO), node);
        }catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("sensor_dto", sensorDTO);
            return "weather/add";
        }
        return "redirect:/weather";
    }
    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }
}
