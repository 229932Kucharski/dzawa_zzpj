package pl.jawa.psinder.entity;

import lombok.Data;
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
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "walker_id")
    private User walker;

    @Column(name = "status", columnDefinition = "ENUM('waiting', 'accepted', 'canceled')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "connection")
    private List<Chat> messages;


}
