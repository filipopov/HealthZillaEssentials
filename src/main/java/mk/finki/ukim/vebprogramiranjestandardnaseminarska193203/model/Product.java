package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    private List<Category> categories;
    private Integer price;
    private Integer discountedPrice;
    private Integer availablePieces;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private Discount discount;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
    private List<ShoppingCart> shoppingCarts;

    public Product() {}

    public Product(String name, String description, Category category, Integer price, Integer availablePieces, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availablePieces = availablePieces;
        this.manufacturer = manufacturer;
        this.categories = new ArrayList<>();
        this.categories.add(category);
    }

    public Product(String name, String description, List<Category> categories, Integer price, Integer availablePieces, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.availablePieces = availablePieces;
        this.manufacturer = manufacturer;
    }

}
