package weather.example.weatherNow.dto;

public class StatisticDTO {

    private String cityName;

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
