package weather.example.weatherNow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO для поиска по диапазону дат")
public class DateSearchDTO {

    private static final String ERROR_MSG = "Неверный формат ввода даты. Введите дату в формате: ГГГГ-ММ-ДДTчч:мм:ссZ (2022-12-03T23:12:28Z)";

    private static final String REGEX_VALUE = "^[0-9]{4}-[0-1][0-9]-[0-9]{2}T[0-2][0-9]:[0-5][0-9]:[0-5][0-9]Z$";

    private static final String NOT_EMPTY = "Поле не должно быть пустым";

    @NotEmpty(message = NOT_EMPTY)
    @Pattern(regexp = REGEX_VALUE, message = ERROR_MSG)
    @Schema(description = "Нижняя граница диапазона даты в формате ISO 8601", example = "2021-10-20T20:23:23Z")
    private String dateFrom;

    @NotEmpty(message = NOT_EMPTY)
    @Pattern(regexp = REGEX_VALUE, message = ERROR_MSG)
    @Schema(description = "Верхняя граница диапазона даты в формате ISO 8601", example = "2024-03-10T20:23:23Z")
    private String dateTo;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "DateSearchDTO{" +
                "dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}
