package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model;

import lombok.Data;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.Role;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "shop_users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ShoppingCart> shoppingCarts;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OneToMany
    private List<Order> orderList;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    List<Review> reviews;

    @Any(metaColumn = @Column(name = "property_type"), fetch = FetchType.EAGER)
    @AnyMetaDef(name = "review", metaType = "string", idType = "long", metaValues = {
        @MetaValue(value = "P", targetEntity = PraiseReview.class),
        @MetaValue(value = "C", targetEntity = ComplaintReview.class)
    })
    @JoinColumn(name = "property_id")
    public List<Review> getReviews(){
        return reviews;
    };

    public User(String username, String password, String name, String surname, String email, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.orderList = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public User(String username, String password, String name, String surname, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = Role.ROLE_USER;
        this.orderList = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
