package pl.jawa.psinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.enums.Status;
import pl.jawa.psinder.repository.ConnectionRepository;
import pl.jawa.psinder.service.ConnectionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping("")
    public List<Connection> getConnections() {
        return connectionService.getConnections();
    }

    @GetMapping(value = "", params = "id")
    public Optional<Connection> getConnectionById(@RequestParam("id") long id) {
        return connectionService.getConnectionById(id);
    }

    @GetMapping(value = "", params = "ownerid")
    public List<Connection> getConnectionsByOwnerId(@RequestParam("ownerid") long id) {
        return connectionService.getConnectionsByOwnerId(id);
    }

    @GetMapping(value = "", params = {"ownerid", "keyword"})
    public List<Connection> getConnectionsByOwnerIdWithKeyWord(@RequestParam("ownerid") long id, @RequestParam("keyword") String keyword) {
        return connectionService.getConnectionsByOwnerIdWithKeyWord(id, keyword.toLowerCase());
    }

    @GetMapping(value = "", params = "walkerid")
    public List<Connection> getConnectionsByWalkerId(@RequestParam("walkerid") long id) {
        return connectionService.getConnectionsByWalkerId(id);
    }

    @GetMapping(value = "", params = {"walkerid", "keyword"})
    public List<Connection> getConnectionsByWalkerIdWithKeyWord(@RequestParam("walkerid") long id, @RequestParam("keyword") String keyword) {
        return connectionService.getConnectionsByWalkerIdWithKeyWord(id, keyword);
    }

    @GetMapping(value = "", params = {"ownerid", "status"})
    public List<Connection> getConnectionsByStatus(@RequestParam("ownerid") long id, @RequestParam("status") String status) {
        return connectionService.getConnectionsByStatus(id, status);
    }

    @PostMapping(path = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Connection newConnection) {
        try {
            String returnMessage = String.valueOf(connectionService.createConnection(newConnection));

            return new ResponseEntity<>(returnMessage, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not create connection", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PutMapping(path = "status-update",
            produces = MediaType.APPLICATION_JSON_VALUE, params = {"connectionid", "status"})
    public ResponseEntity<String> statusChange(@RequestParam("connectionid") long id, @RequestParam("status") String status) {
        try {
            String returnMessage = String.valueOf(connectionService.statusUpdate(id, status));

            return new ResponseEntity<>(returnMessage, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not modify connection status", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping(path = "delete", params = "id")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id) {
        try {
            connectionService.deleteConnection(id);
          
            return new ResponseEntity<>("Connection deleted", HttpStatus.OK);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ResponseEntity<>("Could not delete connection", HttpStatus.BAD_REQUEST);
        }
    }
}
