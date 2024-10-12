package weather.example.weatherNow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import weather.example.weatherNow.dto.DateSearchDTO;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.service.WeatherServiceNew;
import weather.example.weatherNow.util.DateDTOValidator;
import weather.example.weatherNow.util.ExceptionBuilder;
import weather.example.weatherNow.util.WeatherErrorResponse;
import weather.example.weatherNow.util.WeatherException;

import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class Controller {

    //    @GetMapping("")
//    public ResponseEntity getMessage(){
//        return ResponseEntity.ok(service.getAllCities());
//    }

    private WeatherServiceNew weatherService;

    private DateDTOValidator dateDTOValidator;

    @Autowired
    public Controller(WeatherServiceNew weatherService, DateDTOValidator dateDTOValidator) {
        this.weatherService = weatherService;
        this.dateDTOValidator = dateDTOValidator;
    }

    @GetMapping("/newCities")
    public ResponseEntity saveCities() {
        weatherService.saveCities();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/newMeasurements")
    public ResponseEntity saveMeasurements(){
        weatherService.saveMeasurements();
        return ResponseEntity.ok().build();
    }
//    @GetMapping("/statistics")
//    public List<StatisticDTO> getStatistics() {
//        return weatherService.getStatisticsForEveryCity();
//    }

//    @GetMapping("/statistics")
//    public List<StatisticDTO> getStatistics(
//            @RequestParam(value = "dateFrom")String dateFrom,
//            @RequestParam(value = "dateTo")String dateTo) {
//
//        return weatherService.getStatisticsForEveryCityBetweenDates(dateFrom,dateTo);
//    }

    @GetMapping("/statistics")
    public ResponseEntity getStatisticsWithDTO(
            @RequestBody @Valid DateSearchDTO dateDTO, BindingResult bindingResult) {

        dateDTOValidator.validate(dateDTO,bindingResult);
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);

        return ResponseEntity.ok(weatherService.getStatisticsForEveryCityBetweenDates(dateDTO.getDateFrom(), dateDTO.getDateTo()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteAllMeasurements(){
        weatherService.deleteAllMeasurementsWithCities();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    private ResponseEntity<WeatherErrorResponse> handlerException(WeatherException e) {
        WeatherErrorResponse response = new WeatherErrorResponse(
                e.getMessage(),
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
