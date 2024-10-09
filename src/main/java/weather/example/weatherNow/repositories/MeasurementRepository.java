package weather.example.weatherNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.model.MeasurementModel;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementModel, Integer> {

    @Query("SELECT new weather.example.weatherNow.dto.StatisticDTO(c.cityName, AVG(m.temperature)) " +
            "FROM MeasurementModel m JOIN m.cityModel c " +
            "Where m.measurementDate BETWEEN :dateFrom AND :dateTo group by c.cityId")
    List<StatisticDTO> findAverageTemperatureByCityModelWithDates(
            @Param("dateFrom") ZonedDateTime dateFrom,
            @Param("dateTo") ZonedDateTime dateTo
    );


}
