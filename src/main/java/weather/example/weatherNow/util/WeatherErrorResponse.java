package weather.example.weatherNow.util;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Класс, возвращающий текст ошибки пользователю в случае некорректно введенных данных")
public class WeatherErrorResponse {

    @Schema(description = "Текст сообщения об ошибке", example = "Неверный формат ввода даты. Введите дату в формате: ГГГГ-ММ-ДДTчч:мм:ссZ (2022-12-03T23:12:28Z)")
    private String message;

    @Schema(description = "Временная метка (timestamp)", example = "1729178966070")
    private long timestamp;

    public WeatherErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
