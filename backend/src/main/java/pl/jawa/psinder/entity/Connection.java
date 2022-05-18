package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.jawa.psinder.enums.Status;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "connection")
@Data
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "walker_id")
    private User walker;

    @Column(name = "status", columnDefinition = "ENUM('waiting', 'accepted', 'canceled')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "connection")
    private List<Chat> messages;


}
