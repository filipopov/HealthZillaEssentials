package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Manufacturer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private Country country;


    // NAPOMENA okolu brisenjeto na proizvoditel:
    // Brisenjeto na proizvoditel povlekuva brisenje na site produkti asocirani so nego
    // Mora da bideme vnimatelni koga koristime cascade Remove koga stanuva zbor za _toMany vrski. Vo najlosoto scenario ova moze da ja isprazne celata baza.
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.REMOVE)
    private List<Product> productList;

    public Manufacturer(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer() {}
}
