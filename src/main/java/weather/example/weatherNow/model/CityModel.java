package weather.example.weatherNow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "city")
public class CityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer cityId;

    // на данный момент regex для кириллицы
    @NotEmpty(message = "ФИО обязательно для заполнения")
    @Size(min = 3, max = 50, message = "Название города может содержать от 3 до 50 символов")
//    @Pattern(regexp = "[A-Z][a-z]+", message = "Название города нужно писать на английском")
    @Column(name = "city_name")
    private String cityName;

    @OneToMany(mappedBy = "cityModel")
    @Cascade(value = {
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.REFRESH})
    private List<MeasurementModel> measurements;

    public CityModel() {
    }

    public CityModel(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public List<MeasurementModel> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementModel> measurements) {
        this.measurements = measurements;
    }
}
