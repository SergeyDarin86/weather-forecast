package weather.example.weatherNow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для вывода результатов поиска")
public class StatisticDTO {

    @Schema(description = "Название города", example = "Moscow")
    private String cityName;

    @Schema(description = "Средняя температура за указанный период", example = "12.3")
    private Double avgTemperature;

    public StatisticDTO(String cityName, Double avgTemperature) {
        this.cityName = cityName;
        this.avgTemperature = avgTemperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }
}
