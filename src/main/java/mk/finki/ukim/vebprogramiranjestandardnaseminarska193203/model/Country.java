package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String address;


    // NAPOMENA okolu brisenjeto na zemja:
    // Brisenjeto na zemja povlekuva brisenje na site proizvoditeli od taa zemja, sto pak povlekuva brisenje na site produkti asocirani so tie proizvoditeli.
    // Mora da bideme vnimatelni koga koristime cascade Remove koga stanuva zbor za _toMany vrski. Vo najlosoto scenario ova moze da ja isprazne celata baza.
    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<Manufacturer> manufacturerList;

    public Country(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public Country() {}
}
