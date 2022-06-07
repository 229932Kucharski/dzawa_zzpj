package pl.jawa.psinder.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AddressDistanceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "http://www.mapquestapi.com/directions/v2/";
    private static final String API_KEY = "PiyGfpjsJ9Y8hubYJeQAwmKHAAqJ5iW8";

    /**
     * @param from is an address in format "city, street"
     * @param to is an address in format "city, street"
     * @return distance between given addresses in km as Double, if address is incorrect, returns -1.0
     */
    public Double getDistance(String from, String to) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "route?key={apiKey}" +
                "&from={from}&to={to}&unit=k&routeType=pedestrian", String.class, API_KEY, from, to);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody()).path("route");
        JsonNode error = root.path("routeError").path("errorCode");
        if (error.asInt() == -400) {
            return root.path("distance").asDouble();
        } else {
            return -1.0;
        }
    }

}
