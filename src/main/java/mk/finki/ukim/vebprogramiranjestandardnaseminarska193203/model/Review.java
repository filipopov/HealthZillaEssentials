package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String message;

    public Review(String message) {
        this.message = message;
    }

    public Review() {}
}
