package pl.jawa.psinder.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.enums.Status;
import pl.jawa.psinder.repository.ConnectionRepository;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "", params = "id")
    public Optional<Connection> getConnectionById(@RequestParam("id") long id) {
        return connectionRepository.findById(id);
    }

    @GetMapping(value = "", params = "ownerid")
    public List<Connection> getConnectionsByOwnerId(@RequestParam("ownerid") int id) {
        return connectionRepository.findByOwnerId(id);
    }

    @GetMapping(value = "", params = {"ownerid", "keyword"})
    public List<Connection> getConnectionsByOwnerIdWithKeyWord(@RequestParam("ownerid") int id, @RequestParam("keyword") String keyword) {
        System.out.print(keyword.toLowerCase());
        return connectionRepository.findByOwnerIdWithKeyWord(id, keyword.toLowerCase());
    }

    @GetMapping(value = "", params = "walkerid")
    public List<Connection> getConnectionsByWalkerId(@RequestParam("walkerid") int id) {
        return connectionRepository.findByWalkerId(id);
    }

    @GetMapping(value = "", params = {"walkerid", "keyword"})
    public List<Connection> getConnectionsByWalkerIdWithKeyWord(@RequestParam("walkerid") int id, @RequestParam("keyword") String keyword) {
        return connectionRepository.findByWalkerIdWithKeyWord(id, keyword);
    }

    @GetMapping(value = "", params = {"ownerid", "status"})
    public List<Connection> getConnectionsByStatus(@RequestParam("ownerid") int id, @RequestParam("status") String status) {
        return connectionRepository.findByStatus(id, status);
    }

    @PostMapping(path = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Connection newConnection) {
        try {
            Connection connection = connectionRepository.save(newConnection);

            return new ResponseEntity<>(String.valueOf(connection.getId()), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not create connection", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "status-update",
            produces = MediaType.APPLICATION_JSON_VALUE, params = {"connectionid", "status"})
    public ResponseEntity<String> statusChange(@RequestParam("connectionid") long id, @RequestParam("status") String status) {
        try {
            Connection connection = connectionRepository.getById(id);
            connection.setStatus(Status.valueOf(status));

            connection = connectionRepository.save(connection);
            return new ResponseEntity<>(String.valueOf(connection.getId()), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not modify connection status", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "delete", params = "id")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id) {
        try {
            connectionRepository.deleteById(id);

            return new ResponseEntity<>("Connection deleted", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not delete connection", HttpStatus.BAD_REQUEST);
        }
    }
}
