package pl.jawa.psinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jawa.psinder.entity.Connection;
import pl.jawa.psinder.enums.Status;
import pl.jawa.psinder.repository.ConnectionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    public List<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    public Optional<Connection> getConnectionById(long id) {
        return connectionRepository.findById(id);
    }

    public List<Connection> getConnectionsByOwnerId(long id) {
        return connectionRepository.findByOwnerId(id);
    }

    public List<Connection> getConnectionsByOwnerIdWithKeyWord(long id, String keyword) {
        return connectionRepository.findByOwnerIdWithKeyWord(id, keyword.toLowerCase());
    }

    public List<Connection> getConnectionsByWalkerId(long id) {
        return connectionRepository.findByWalkerId(id);
    }

    public List<Connection> getConnectionsByWalkerIdWithKeyWord(long id, String keyword) {
        return connectionRepository.findByWalkerIdWithKeyWord(id, keyword);
    }

    public List<Connection> getConnectionsByStatus(long id, String status) {
        return connectionRepository.findByStatus(id, status);
    }

    public long createConnection(Connection newConnection) throws Exception {
        try {
            Connection connection = connectionRepository.save(newConnection);
            return connection.getId();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public long statusUpdate(long id, String status) throws Exception {
        try {
            Connection connection = connectionRepository.getById(id);
            connection.setStatus(Status.valueOf(status));

            connection = connectionRepository.save(connection);

            return connection.getId();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean deleteConnection(long id) throws Exception {
        try {
            connectionRepository.deleteById(id);

            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
