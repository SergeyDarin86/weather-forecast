package weather.example.weatherNow.util;

public class WeatherException extends RuntimeException{
    public WeatherException(String errorMsg) {
        super(errorMsg);
    }
}
