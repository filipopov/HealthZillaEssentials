package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ComplaintReview extends Review{
    @Id
    @GeneratedValue
    private Long id;

    public ComplaintReview(String message) {
        super(message);
    }

    public ComplaintReview() {}

    @Override
    public String getMessage() {
        return "Поплака: " + super.getMessage();
    }
}
