package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class PraiseReview extends Review{
    @Id
    @GeneratedValue
    private Long id;

    public PraiseReview(String message) {
        super(message);
    }

    public PraiseReview() {}

    @Override
    public String getMessage() {
        return "Пофалба: " + super.getMessage();
    }
}
