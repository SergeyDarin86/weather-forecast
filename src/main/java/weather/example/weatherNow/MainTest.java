package weather.example.weatherNow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainTest {
    public static void main(String[] args) {
//
//        List<City> cities = new ArrayList<>();
//        cities.add(new City("Samara", ZonedDateTime.now().minusMonths(1),23.4));
//        cities.add(new City("Moscow",ZonedDateTime.now().minusMonths(2),-12.3));
//        cities.add(new City("Togliatti", ZonedDateTime.now(),0.4));
//        cities.add(new City("Vologda", ZonedDateTime.now().minusMinutes(60), -4));
//        cities.add(new City("Volgograd", ZonedDateTime.now().minusYears(1),-25.7));
//
//        Random random = new Random();
//
//        for (int i = 0; i < cities.size(); i++){
//            System.out.println(cities.get(random.nextInt(cities.size())).getTemperature());
//        }
        double d = 23.4;
        System.out.println(d);
//        String str = "2024-10-07 19:19:00";

        String str = "2024-10-07 19:19:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);
    }
}
