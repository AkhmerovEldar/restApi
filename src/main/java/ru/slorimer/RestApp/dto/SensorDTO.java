package ru.slorimer.RestApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotNull(message = "empty name")
    @Size(min = 3, max = 30, message = "name should be between 3 and 50 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
