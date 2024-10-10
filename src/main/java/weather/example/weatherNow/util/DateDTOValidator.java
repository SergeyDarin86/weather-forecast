package weather.example.weatherNow.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import weather.example.weatherNow.dto.DateSearchDTO;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class DateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DateSearchDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Start method validate(target, errors) for dateDTOValidator, target is: {} ", target);
        DateSearchDTO dateDTO = (DateSearchDTO) target;
        try {
            if (ZonedDateTime.parse(dateDTO.getDateFrom()).isAfter(ZonedDateTime.parse(dateDTO.getDateTo())) || dateDTO.getDateFrom().equals(dateDTO.getDateTo())) {
                errors.rejectValue("dateFrom", "", " dateFrom должна быть до dateTo");
            }
        } catch (DateTimeParseException ignored) {
        }

    }

}
