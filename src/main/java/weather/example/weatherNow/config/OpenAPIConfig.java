package weather.example.weatherNow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${weather.openapi.dev-url}")
    private String devUrl;

    public static final String DESCRIPTION_WEATHER_FORECAST = "Сервис предназначен для:" +
            " \n- сбора данных о погоде для произвольных городов;" +
            " \n- вывода средней температуры за определенный период для каждого города; " +
            " \n- удаления всех измерений.";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("swd86@mail.ru");
        contact.setName("Sergey Darin");


        Info info = new Info()
                .title("Руководство использования сервиса API")
                .version("1.0")
                .contact(contact)
                .description(DESCRIPTION_WEATHER_FORECAST);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}