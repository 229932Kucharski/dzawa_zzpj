package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jawa.psinder.enums.Days;

import javax.persistence.*;

@Entity
@Table(name = "petaddress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

}
