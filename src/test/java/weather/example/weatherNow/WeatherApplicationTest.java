package weather.example.weatherNow;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import weather.example.weatherNow.controller.Controller;
import weather.example.weatherNow.dto.DateSearchDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/create-tables-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-from-tables-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
class WeatherApplicationTest {

    @Autowired
    private Controller controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    DateSearchDTO searchDTO;

    @BeforeEach
    void setUp() {
        searchDTO = new DateSearchDTO();
        searchDTO.setDateFrom("2020-11-16T04:25:03Z");
        searchDTO.setDateTo("2024-10-14T04:25:03Z");
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void saveCities() throws Exception {
        this.mockMvc
                .perform(get("/weather/newCities"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void saveMeasurements() throws Exception {
        this.mockMvc.perform(get("/weather/newMeasurements"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getStatisticsBetweenDates() throws Exception {
        this.mockMvc
                .perform(post("/weather/statistics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTO)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void getStatisticsBetweenDatesWithThrowException() throws Exception {
        String wrongDateFormat = "2024-10-14T04:25:03";
        searchDTO.setDateTo(wrongDateFormat);
        this.mockMvc
                .perform(get("/weather/statistics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(searchDTO)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteAllMeasurements() throws Exception {
        this.mockMvc.perform(delete("/weather/delete"))
                .andExpect(status().isOk());
    }

}