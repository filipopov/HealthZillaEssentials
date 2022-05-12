package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BillingCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolderName;

    private Integer cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer ccv;

    @ManyToOne
    private Order order;

    public BillingCard(String cardHolderName, Integer cardNumber, Integer expiryMonth, Integer expiryYear, Integer ccv, Order order) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.ccv = ccv;
        this.order = order;
    }

    public BillingCard() {
    }
}
