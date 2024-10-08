package weather.example.weatherNow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import weather.example.weatherNow.config.CitiesList;
import weather.example.weatherNow.config.City;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.model.CityModel;
import weather.example.weatherNow.model.MeasurementModel;
import weather.example.weatherNow.repositories.CityRepository;
import weather.example.weatherNow.repositories.MeasurementRepository;
import weather.example.weatherNow.util.CityResponse;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

//TODO:
// +- 1) добавить ограничения на формат вводимых дат при формировании статистики
// + 2) добавить перехватываемые исключения и сообщения об ошибках
// - 3) тестирование (интеграционное/unit)
// - 4) запуск на docker
// - 5) swagger-документация
// - 6) README.md
// - 7) вынести appId - в отдельный параметр
// +- 8) добавить логирование в проект

@Slf4j
@Service
@Transactional(readOnly = true)
public class WeatherServiceNew {

    private CityRepository cityRepository;

    private MeasurementRepository measurementRepository;

    private CitiesList citiesList;

    private RestTemplate restTemplate;

    @Autowired
    public WeatherServiceNew(CityRepository cityRepository, MeasurementRepository measurementRepository, CitiesList citiesList, RestTemplate restTemplate) {
        this.cityRepository = cityRepository;
        this.measurementRepository = measurementRepository;
        this.citiesList = citiesList;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Scheduled(initialDelay = 1)
    public void saveCities() {
        log.info("Start method saveCities() for weatherService, time is: {} ", LocalDateTime.now());
        for (City city : citiesList.getCities()) {

            Optional<CityModel> cityModelOptional = cityRepository.findCityModelByCityName(city.getName());

            CityModel cityModel = new CityModel();
            if (cityModelOptional.isEmpty()) {
                cityModel.setCityName(city.getName());
                cityRepository.save(cityModel);
            }
        }
    }

    @Transactional
    @Scheduled(cron = "${cron.saveMeasurements}")
    public void saveMeasurements() {
        log.info("Start method saveMeasurements() for weatherService, time is: {} ", LocalDateTime.now());
        String units = "metric";
        String appId = "17f981443364cc861d066e1422c05746";

        for (City city : citiesList.getCities()) {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.getName() + "&units=" + units + "&appid=" + appId;
            CityResponse cityResponse = restTemplate.getForObject(url, CityResponse.class);
            MeasurementModel measurementModel = new MeasurementModel(ZonedDateTime.now(),
                    cityResponse.getMain().getTemp(),
                    cityRepository.findCityModelByCityName(city.getName()).get());

            measurementRepository.save(measurementModel);
        }
    }

    // на данный момент вывожу статистику для ВСЕХ городов, которые лежат в базе
    // даже если он не указан в конфигурационном файле
    //TODO: можно также прогонять через for-each SitesList и находить по siteName

    public List<StatisticDTO> getStatisticsForEveryCityBetweenDates(String dateFromStr, String dateToStr) {
        log.info("Start method getStatisticsForEveryCityBetweenDates(dateFromStr, dateToStr) for weatherService, dateFromStr is: {}, dateToStr is : {} ", dateFromStr, dateToStr);
        ZonedDateTime dateFrom = ZonedDateTime.parse(dateFromStr);
        ZonedDateTime dateTo = ZonedDateTime.parse(dateToStr);
        return measurementRepository.findAverageTemperatureByCityModelWithDates(dateFrom,dateTo);
    }

    @Transactional
    public void deleteAllMeasurementsWithCities(){
        log.info("Start method deleteAllMeasurementsWithCities() for weatherService, time is: {} ", LocalDateTime.now());
        measurementRepository.deleteAll();
        cityRepository.deleteAll();
    }

}
