package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.config;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.*;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.Role;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.*;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataHolder {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final CountryService countryService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ReviewService reviewService;

    public InitialDataHolder(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService, CountryService countryService, AuthService authService, PasswordEncoder passwordEncoder, UserService userService, ReviewService reviewService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.countryService = countryService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PostConstruct
    public void init(){
        Category c1 = new Category("Health", "Health Description");
        Category c2 = new Category("Strength", "Strength Description");
        Category c3 = new Category("Energy", "Energy Description");
        Category c4 = new Category("Sleep", "Sleep Description");
        Category c5 = new Category("Immune System", "Immune System Description");
        Category c6 = new Category("Fat Loss", "Fat Loss Description");
        List<Category> list1 = new ArrayList<>();
        list1.add(c1);
        list1.add(c2);
        List<Category> list2 = new ArrayList<>();
        list2.add(c1);
        list2.add(c2);
        list2.add(c4);
        List<Category> list3 = new ArrayList<>();
        list3.add(c4);
        list3.add(c5);
        List<Category> list4 = new ArrayList<>();
        list4.add(c6);
        this.categoryService.save(c1);
        this.categoryService.save(c2);
        this.categoryService.save(c3);
        this.categoryService.save(c4);
        this.categoryService.save(c5);
        this.categoryService.save(c6);

        this.userService.save(new User("filipopov", passwordEncoder.encode("fp"), "filip", "popov", "filip.popov13@gmail.com", Role.ROLE_ADMIN));
        this.userService.save(new User("petkopetkov", passwordEncoder.encode("pp"), "petko", "petkov", "thebountyslayer@gmail.com", Role.ROLE_USER));
        this.userService.save(new User("trajkotrajkov", passwordEncoder.encode("tt"), "trajko", "trajkov", "thebountyslayer@gmail.com", Role.ROLE_USER));

        Country country1 = new Country("Makedonija", "Bitola", "Mirce Acev");
        Country country2 = new Country("United Kingdom", "London", "Winston Churchil");
        Country country3 = new Country("USA", "New York","8th Avenue");
        Country country4 = new Country("Switzerland", "Zurich","Zurich Address");

        this.countryService.save(country1);
        this.countryService.save(country2);
        this.countryService.save(country3);
        this.countryService.save(country4);

        Manufacturer manufacturer1 = new Manufacturer("Alkaloid", country1);
        Manufacturer manufacturer2 = new Manufacturer("Zegin", country2);

        this.manufacturerService.save(manufacturer1);
        this.manufacturerService.save(manufacturer2);

        this.productService.save(new Product("Produkt1","description1", list1, 500, 10, manufacturer1));
        this.productService.save(new Product("Produkt2","description2", list2, 1000, 14, manufacturer2));
        this.productService.save(new Product("Produkt3", "description3", list2, 1500, 1, manufacturer2));
        this.productService.save(new Product("Produkt4", "description4", list3, 300, 12, manufacturer1));
        this.productService.save(new Product("Produkt5", "description5", list1, 1600, 9, manufacturer2));
        this.productService.save(new Product("Produkt6", "description6", list3, 800, 5, manufacturer1));
        this.productService.save(new Product("Produkt7", "description7", list4, 2500, 50, manufacturer2));


        Review complaintReview = new ComplaintReview("Ова е поплака");
        Review praiseReview = new PraiseReview("Ова е пофалба");

        this.reviewService.save(complaintReview);
        this.reviewService.save(praiseReview);

        List<Review> reviews = new ArrayList<>();
        reviews.add(complaintReview);
        reviews.add(praiseReview);

        User temp = new User("Korisnik", passwordEncoder.encode("k"),
                "Korisnik", "Korisnik", "temp@gmail.com", Role.ROLE_USER);

        temp.setReviews(reviews);

        this.userService.save(temp);

        complaintReview.setUser(temp);
        praiseReview.setUser(temp);

        this.reviewService.save(complaintReview);
        this.reviewService.save(praiseReview);
    }
}
