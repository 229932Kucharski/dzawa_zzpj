package pl.jawa.psinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class Rating {

    public Rating(int rate, String comment, User fromUser, User toUser) {
        this.rate = rate;
        this.comment = comment;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "rating")
    private int rate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

}
