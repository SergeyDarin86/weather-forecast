package weather.example.weatherNow.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class MeasurementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Integer measurementId;

    @Column(name = "measurement_date")
    private LocalDateTime measurementDate;

    @Column(name = "temperature")
    private Double temperature;


    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private CityModel cityModel;

    public MeasurementModel() {
    }

    public MeasurementModel(LocalDateTime measurementDate, Double temperature, CityModel cityModel) {
        this.measurementDate = measurementDate;
        this.temperature = temperature;
        this.cityModel = cityModel;
    }

    public Integer getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    public LocalDateTime getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDateTime measurementDate) {
        this.measurementDate = measurementDate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
