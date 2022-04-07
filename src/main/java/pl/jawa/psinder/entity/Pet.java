package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pet")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "race")
    private String race;

    @Column(name = "size")
    private String size;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private List<PetAvailability> availabilities;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pet")
    private PetAddress address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    @JsonIgnore
    private List<Connection> connections;
}
