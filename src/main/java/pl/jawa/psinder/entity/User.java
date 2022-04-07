package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Pet> pets;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromUser")
    @JsonIgnore
    private List<Rating> givenRatings;

    // TODO Check if correct
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toUser")
    @JsonIgnore
    private List<Rating> receivedRatings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonIgnore
    private List<Connection> connectionsAsOwner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "walker")
    @JsonIgnore
    private List<Connection> connectionsAsWalker;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Chat> messages;

}
