package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer percentage;

    @OneToMany(mappedBy = "discount")
    private List<Product> productsWithDiscount;

    public Discount(Integer percentage) {
        this.percentage = percentage;
        this.productsWithDiscount = new ArrayList<>();
    }

    public Discount() {
    }
}
