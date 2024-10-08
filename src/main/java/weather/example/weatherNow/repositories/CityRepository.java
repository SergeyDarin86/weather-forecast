package weather.example.weatherNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weather.example.weatherNow.model.CityModel;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityModel,Integer> {
    Optional<CityModel>findCityModelByCityName(String name);
}
