package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidNumberException;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order_fill")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String number;
    private String city;
    private String address;
    private String typePayment;

    @OneToOne
    private ShoppingCart shoppingCart;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<BillingCard> billingCard;

    public Order(String name, String surname, String number, String city, String address, String typePayment, ShoppingCart shoppingCart) {
        this.name = name;
        this.surname = surname;
        try{
            Integer.parseInt(number);
            this.number = number;
        }
        catch (NumberFormatException exception){
            throw new InvalidNumberException();
        }

        this.city = city;
        this.address = address;
        this.typePayment = typePayment;
        this.shoppingCart = shoppingCart;
    }

    public Order() {
    }

    public String eta(){
        Date date = new Date();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        Integer day1 = Integer.parseInt(sdf1.format(date)) + 1;
        String fromDay = day1.toString();

        SimpleDateFormat month = new SimpleDateFormat("MM");
        String monthStr = month.format(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
        Integer day2 = Integer.parseInt(sdf2.format(date)) + 4;
        String toDay = day2.toString();


        return fromDay+"."+monthStr+".2022 - "+toDay+"."+monthStr+".2022";
    }
}
