package weather.example.weatherNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.model.CityModel;
import weather.example.weatherNow.model.MeasurementModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementModel, Integer> {
    //    @Query(value = "SELECT\n" +
//            "    c.city_name,\n" +
//            "    AVG(m.temperature) AS average_temperature\n" +
//            "FROM\n" +
//            "    city c\n" +
//            "        JOIN\n" +
//            "    measurement m ON c.city_id = m.city_id\n" +
//            "GROUP BY\n" +
//            "    c.city_id, c.city_name", nativeQuery = true)
    @Query("SELECT new weather.example.weatherNow.dto.StatisticDTO(c.cityName, AVG(m.temperature)) " +
            "FROM MeasurementModel m JOIN m.cityModel c " +
            "GROUP BY c.cityId")
    List<StatisticDTO> findAverageTemperatureByCity();

//    and (m.cityModel.cityName like :cityName)

    @Query("SELECT new weather.example.weatherNow.dto.StatisticDTO(c.cityName, AVG(m.temperature)) " +
            "FROM MeasurementModel m JOIN m.cityModel c " +
            "Where m.measurementDate BETWEEN :dateFrom AND :dateTo group by c.cityId")
    List<StatisticDTO> findAverageTemperatureByCityModelWithDates(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );


}
