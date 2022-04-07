package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chat")
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

}
