package weather.example.weatherNow.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import weather.example.weatherNow.dto.DateSearchDTO;
import weather.example.weatherNow.dto.StatisticDTO;
import weather.example.weatherNow.util.WeatherErrorResponse;

@Tag(name = "API сервиса погоды")
public interface MeasurementResource {

    @Operation(
            summary = "Сохранение новых городов.",
            description = "Данный метод выполняется первым автоматически один раз при каждом запуске приложения."
    )
    @ApiResponse(responseCode = "200", description = "Метод успешно выполнен.")
    @GetMapping
    ResponseEntity saveCities();

    @Operation(
            summary = "Сохранение данных о погоде.",
            description = "Данный метод выполняется автоматически каждую минуту " +
                    "и собирает данные о погоде для городов, указанных в конфигурационном файле."
    )
    @ApiResponse(responseCode = "200", description = "Метод успешно выполнен.")
    @GetMapping
    ResponseEntity saveMeasurements();

    @Operation(
            summary = "Получение средней температуры за указанный период для каждого города.",
            description = "Необходимо ввести диапазон дат в необходимом формате ISO 8601."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Метод успешно выполнен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = StatisticDTO.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WeatherErrorResponse.class))
            )
    })
    @GetMapping()
    ResponseEntity getStatisticsWithDTO(@RequestBody @Valid DateSearchDTO dateSearchDTO, BindingResult bindingResult);

    @Operation(
            summary = "Удаление всех данных о погоде.",
            description = "Удаление всех данных о городах и погоде."
    )
    @ApiResponse(responseCode = "200", description = "Метод успешно выполнен")
    @DeleteMapping()
    ResponseEntity deleteAllMeasurements();

}
