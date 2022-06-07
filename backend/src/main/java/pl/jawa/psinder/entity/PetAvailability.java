package pl.jawa.psinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import pl.jawa.psinder.enums.Days;

import javax.persistence.*;

@Entity
@Table(name = "petavailability")
@Data
public class PetAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    @ToString.Exclude
    private Pet pet;

    @Column(name = "day", columnDefinition = "ENUM('pn', 'wt', 'sr', 'cz', 'pt', 'so', 'nd')")
    @Enumerated(EnumType.STRING)
    private Days day;

    @Column(name = "from_hour")
    private String fromHour;

    @Column(name = "to_hour")
    private String toHour;

}
