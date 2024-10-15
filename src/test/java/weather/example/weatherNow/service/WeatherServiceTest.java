package weather.example.weatherNow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import weather.example.weatherNow.config.CitiesList;
import weather.example.weatherNow.config.City;
import weather.example.weatherNow.model.CityModel;
import weather.example.weatherNow.model.MeasurementModel;
import weather.example.weatherNow.repositories.CityRepository;
import weather.example.weatherNow.repositories.MeasurementRepository;
import weather.example.weatherNow.util.CityResponse;
import weather.example.weatherNow.util.Main;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

class WeatherServiceTest {

    private CityRepository cityRepository = Mockito.mock(CityRepository.class);

    private MeasurementRepository measurementRepository = Mockito.mock(MeasurementRepository.class);

    private CitiesList citiesList = Mockito.mock(CitiesList.class);

    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    @InjectMocks
    private WeatherService weatherService;

    private City city;

    private CityResponse cityResponse;

    private Main main;

    private String dateFromStr;

    private String dateToStr;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        city = new City();
        city.setName("London");

        main = new Main();
        main.setTemp(25.0);

        cityResponse = new CityResponse();
        cityResponse.setMain(main);

        dateFromStr = "2020-11-16T04:25:03Z";
        dateToStr = "2021-11-16T04:25:03Z";
    }


    @Test
    void testSaveCitiesWhenCityDoesNotExistShouldSaveCity() {
        when(citiesList.getCities()).thenReturn(Collections.singletonList(city));
        when(cityRepository.findCityModelByCityName("London")).thenReturn(Optional.empty());

        weatherService.saveCities();
        verify(cityRepository, times(1)).save(any(CityModel.class));
    }

    @Test
    public void testSaveCitiesWhenCityExistsShouldNotSaveCity() {
        when(citiesList.getCities()).thenReturn(Collections.singletonList(city));
        when(cityRepository.findCityModelByCityName("London")).thenReturn(Optional.of(new CityModel()));

        weatherService.saveCities();
        verify(cityRepository, times(0)).save(any(CityModel.class));
    }

    @Test
    void saveMeasurements() {
        when(citiesList.getCities()).thenReturn(Collections.singletonList(city));
        when(cityRepository.findCityModelByCityName(city.getName())).thenReturn(Optional.of(new CityModel(city.getName())));
        when(restTemplate.getForObject(anyString(), Mockito.eq(CityResponse.class))).thenReturn(cityResponse);

        weatherService.saveMeasurements();
        verify(cityRepository, times(1)).findCityModelByCityName(city.getName());
        verify(restTemplate, times(1)).getForObject(anyString(), Mockito.eq(CityResponse.class));
        verify(measurementRepository, times(1)).save(Mockito.any(MeasurementModel.class));
    }

    @Test
    void getStatisticsForEveryCityBetweenDates() {
        ZonedDateTime dateFrom = ZonedDateTime.parse(dateFromStr);
        ZonedDateTime dateTo = ZonedDateTime.parse(dateToStr);

        weatherService.getStatisticsForEveryCityBetweenDates(dateFromStr, dateToStr);
        verify(measurementRepository, times(1)).findAverageTemperatureByCityModelWithDates(dateFrom, dateTo);
    }

    @Test
    void deleteAllMeasurementsWithCities() {
        weatherService.deleteAllMeasurementsWithCities();
        verify(measurementRepository, times(1)).deleteAll();
        verify(cityRepository, times(1)).deleteAll();
    }
}