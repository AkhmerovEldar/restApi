package ru.slorimer.RestApp.dto;

import java.util.List;

public class MeasurementResponse {

    private List<MeasurementsDTO> measurementsDTOList;
    public MeasurementResponse(List<MeasurementsDTO> measurementsDTOList){
        this.measurementsDTOList = measurementsDTOList;
    }

    public List<MeasurementsDTO> getMeasurementsDTOList() {
        return measurementsDTOList;
    }

    public void setMeasurementsDTOList(List<MeasurementsDTO> measurementsDTOList) {
        this.measurementsDTOList = measurementsDTOList;
    }
}
