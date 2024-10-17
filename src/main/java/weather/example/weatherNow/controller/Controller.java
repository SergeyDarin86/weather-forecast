package weather.example.weatherNow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import weather.example.weatherNow.dto.DateSearchDTO;
import weather.example.weatherNow.resource.MeasurementResource;
import weather.example.weatherNow.service.WeatherService;
import weather.example.weatherNow.util.DateDTOValidator;
import weather.example.weatherNow.util.ExceptionBuilder;
import weather.example.weatherNow.util.WeatherErrorResponse;
import weather.example.weatherNow.util.WeatherException;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class Controller implements MeasurementResource {

    private WeatherService weatherService;

    private DateDTOValidator dateDTOValidator;

    @Autowired
    public Controller(WeatherService weatherService, DateDTOValidator dateDTOValidator) {
        this.weatherService = weatherService;
        this.dateDTOValidator = dateDTOValidator;
    }

    @GetMapping("/newCities")
    public ResponseEntity saveCities() {
        weatherService.saveCities();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/newMeasurements")
    public ResponseEntity saveMeasurements() {
        weatherService.saveMeasurements();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/statistics")
    public ResponseEntity getStatisticsWithDTO(
            @RequestBody @Valid DateSearchDTO dateSearchDTO, BindingResult bindingResult) {
        dateDTOValidator.validate(dateSearchDTO, bindingResult);
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);

        return ResponseEntity.ok(weatherService.getStatisticsForEveryCityBetweenDates(dateSearchDTO.getDateFrom(), dateSearchDTO.getDateTo()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteAllMeasurements() {
        weatherService.deleteAllMeasurementsWithCities();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    private ResponseEntity<WeatherErrorResponse> handlerException(WeatherException e) {
        WeatherErrorResponse response = new WeatherErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
