package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String categoryDescription;

    // NAPOMENA okolu brisenjeto na kategorija:
    // Brisenjeto na kategorija povlekuva brisenje na site produkti vo nea
    // Mora da bideme vnimatelni koga koristime cascade Remove koga stanuva zbor za _toMany vrski. Vo najlosoto scenario ova moze da ja isprazne celata baza.
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    private List<Product> products;

    public Category(String name, String categoryDescription, List<Product> products) {
        this.name = name;
        this.categoryDescription = categoryDescription;
        this.products = products;
    }

    public Category(String name, String categoryDescription) {
        this.name = name;
        this.categoryDescription = categoryDescription;
        this.products = new ArrayList<>();
    }

    public Category() {}
}
