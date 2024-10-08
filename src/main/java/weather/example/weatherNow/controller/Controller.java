package weather.example.weatherNow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import weather.example.weatherNow.config.CitiesList;
import weather.example.weatherNow.config.City;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.service.WeatherServiceNew;
import weather.example.weatherNow.util.WeatherErrorResponse;
import weather.example.weatherNow.util.WeatherNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class Controller {

    //    @GetMapping("")
//    public ResponseEntity getMessage(){
//        return ResponseEntity.ok(service.getAllCities());
//    }

    @Autowired
    private WeatherServiceNew weatherService;

    @GetMapping("/city")
    public String getSingleCityWithWeather() {
        weatherService.saveCitiesNew();
        return "";
    }

//    @GetMapping("/statistics")
//    public List<StatisticDTO> getStatistics() {
//        return weatherService.getStatisticsForEveryCity();
//    }

    @GetMapping("/statistics")
    public List<StatisticDTO> getStatistics(
            @RequestParam(value = "dateFrom") String dateFrom,
            @RequestParam(value = "dateTo") String dateTo) {
        return weatherService.getStatisticsForEveryCityBetweenDates(dateFrom,dateTo);
    }

    @DeleteMapping("/delete")
    public void deleteAllMeasurements(){
        weatherService.deleteAllMeasurementsWithCities();
    }

    @ExceptionHandler
    private ResponseEntity<WeatherErrorResponse> handlerException(WeatherNotFoundException e) {
        WeatherErrorResponse response = new WeatherErrorResponse(
                "Weather for City not found",
                System.currentTimeMillis()
        );
        // В HTTP ответе будет тело ответа (response) и статус в заголовке http-ответа
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

//    @PostMapping("create")
//    public ResponseEntity createCity(){
//        service.create();
//        return ResponseEntity.ok().body("Город добавлен");
//    }

//    @PostMapping("create")
//    public ResponseEntity createSingleCity(@RequestBody WeatherModel weatherModel){
//        System.out.println(weatherModel);
//        service.createSingleCity(weatherModel);
//        return ResponseEntity.ok().body("Город добавлен");
//    }
}
