package weather.example.weatherNow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class DateSearchDTO {

    private static final String ERROR_MSG = "Неверный формат ввода даты. Введите дату в формате: ГГГГ-ММ-ДДTчч:мм:ссZ (2022-12-03T23:12:28Z) ";

    private static final String REGEX_VALUE =  "^[0-9]{4}-[0-1][0-9]-[0-9]{2}T[0-2][0-9]:[0-5][0-9]:[0-5][0-9]Z$";

    private static final String NOT_EMPTY = "Поле не должно быть пустым";

    @NotEmpty(message = NOT_EMPTY)
    @Pattern(regexp = REGEX_VALUE, message = ERROR_MSG)
    private String dateFrom;

    @NotEmpty(message = NOT_EMPTY)
    @Pattern(regexp = REGEX_VALUE, message = ERROR_MSG)
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
}
