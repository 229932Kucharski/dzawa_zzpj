package pl.jawa.psinder.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AddressDistanceClientTest {

    @Test
    public void shouldReturnCorrectDistance() throws JsonProcessingException {
        // given
        AddressDistanceClient distanceService = new AddressDistanceClient();

        // when
        Double distance = distanceService.getDistance("Piotrkowska, Łódź", "Zarzewska, Łódź");

        // then
        assertTrue(distance > 1.5 && distance < 2.0);
    }

    @Test
    public void shouldReturnMinusOneOnEmptyAddress() throws JsonProcessingException {
        // given
        AddressDistanceClient distanceService = new AddressDistanceClient();

        // when
        Double distance = distanceService.getDistance("Piotrkowska, Łódź", "");

        // then
        assertEquals(-1.0, distance);
    }

    @Test
    public void shouldReturnMinusOneOnEmptyAddresses() throws JsonProcessingException {
        // given
        AddressDistanceClient distanceService = new AddressDistanceClient();

        // when
        Double distance = distanceService.getDistance("", "");

        // then
        assertEquals(-1.0, distance);
    }

    @Test
    public void shouldReturnMinusOneOnWrongAddress() throws JsonProcessingException {
        // given
        AddressDistanceClient distanceService = new AddressDistanceClient();

        // when
        Double distance = distanceService.getDistance("losowy adres", "brak adresu");

        // then
        assertEquals(-1.0, distance);
    }
}