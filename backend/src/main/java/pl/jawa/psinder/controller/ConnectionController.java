package pl.jawa.psinder.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.repository.ConnectionRepository;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;

    public ConnectionController(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("")
    public List<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    @GetMapping(value = "", params = "ownerid")
    public List<Connection> getConnectionsByOwnerId(@RequestParam("ownerid") int id) {
        return connectionRepository.findByOwnerId(id);
    }

    @GetMapping(value = "", params = "walkerid")
    public List<Connection> getConnectionsByWalkerId(@RequestParam("walkerid") int id) {
        return connectionRepository.findByWalkerId(id);
    }

    @GetMapping(value = "", params = "status")
    public List<Connection> getConnectionsByStatus(@RequestParam("status") String status) {
        return connectionRepository.findByStatus(status);
    }

    @PostMapping(path = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Connection> create(@RequestBody Connection newConnection) {
        Connection connection = connectionRepository.save(newConnection);
        if (connection == null) {
            return new ResponseEntity<>(connection, HttpStatus.METHOD_FAILURE);
        } else {
            return new ResponseEntity<>(connection, HttpStatus.CREATED);
        }
    }
}
