package project.demo.dto;

import jakarta.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty(message = "Name can't be empty !")
    private String name;

    @NotEmpty
    private String city;

    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
