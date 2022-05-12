package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.ShoppingCartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateCreated;
    private String dateCreatedFormat;
    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

    private Integer total;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    @OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.REMOVE)
    private Order order;

    public ShoppingCart(User user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.products = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd yyyy, h:mm a");
        this.dateCreatedFormat = dtf.format(dateCreated);
        this.total = 0;
    }

    public Integer getTotalPriceForCart(){
        for(Product p: products) {
            if(p.getDiscount()==null)
                total += p.getPrice();
            else
                total += p.getDiscountedPrice();
        }
        return total>=2000 ? total : total+150;
    }

    public ShoppingCart() {
    }
}
