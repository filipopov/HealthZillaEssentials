package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ComplaintReview;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.PraiseReview;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Review;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.excel.ReviewExcelExporter;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.OrderService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ReviewService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ShoppingCartService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/my-profile")
public class MyProfileController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;

    public MyProfileController(UserService userService, ShoppingCartService shoppingCartService, OrderService orderService, PasswordEncoder passwordEncoder, ReviewService reviewService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getMyProfilePage(Model model, HttpServletRequest request){
        model.addAttribute("user", this.userService.findByUsername(request.getRemoteUser()).get());
        model.addAttribute("bodyContent", "my-profile-page");
        return "master-template";
    }

    @GetMapping("/reviews")
    public String getReviewsPage(Model model, HttpServletRequest request){
        model.addAttribute("user", this.userService.findByUsername(request.getRemoteUser()).get());
        model.addAttribute("bodyContent", "write-review");
        return "master-template";
    }

    @PostMapping("/writeReview")
    public String getWriteReviewPage(Model model, HttpServletRequest request){

        User user = this.userService.findByUsername(request.getRemoteUser()).get();

        String message = request.getParameter("message");
        String reviewType = request.getParameter("reviewType");

        Review review = null;
        if(reviewType.equals("PraiseReview")){
            review = new PraiseReview(message);
        }
        else{
            review = new ComplaintReview(message);
        }

        this.reviewService.save(review);
        user.getReviews().add(review);

        this.userService.save(user);

        review.setUser(user);
        this.reviewService.save(review);
        model.addAttribute("bodyContent", "review-confirmation");
        return "master-template";
    }

    @GetMapping("/orders")
    public String getMyOrdersPage(Model model, HttpServletRequest request){
        User user = this.userService.findByUsername(request.getRemoteUser()).get();
        model.addAttribute("orders", user.getOrderList());
        model.addAttribute("totalPrice", this.shoppingCartService.getTotal(request.getRemoteUser()));
        model.addAttribute("shoppingCarts", this.shoppingCartService.findAllByUser(user));
        model.addAttribute("bodyContent", "my-orders");
        return "master-template";
    }

    @PostMapping("/edit/{username}")
    public String editProfile(@PathVariable String username, @RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword, Model model){
        User user = this.userService.findByUsername(username).get();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        if(password.equals(repeatPassword))
            user.setPassword(passwordEncoder.encode(password));
        else{
            model.addAttribute("error", "Погрешна лозинка!");
            model.addAttribute("user", user);
            return "my-profile-page";
        }
        this.userService.save(user);

        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "my-profile-page");
        model.addAttribute("success", "Промената е ажурирана!");
        return "my-profile-page";
    }

    @PostMapping("/search")
    public String getOrdersByTime(Model model, @RequestParam String date, HttpServletRequest request){

        User user = this.userService.findByUsername(request.getRemoteUser()).get();
        model.addAttribute("orders", this.orderService.getOrdersByDate(date, user.getOrderList()));
        model.addAttribute("totalPrice", this.shoppingCartService.getTotal(request.getRemoteUser()));
        model.addAttribute("shoppingCarts", this.shoppingCartService.findAllByUser(user));
        model.addAttribute("bodyContent", "my-orders");
        return "master-template";
    }

    @GetMapping("/export/excel")
    public String exportToExcel(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Review_info.xlsx";

        response.setHeader(headerKey, headerValue);
        List<Review> reviews = this.reviewService.findAll();
        ReviewExcelExporter exporter = new ReviewExcelExporter(reviews);
        exporter.export(response);

        model.addAttribute("user", this.userService.findByUsername(request.getRemoteUser()).get());
        model.addAttribute("bodyContent", "reviews");
        return "master-template";
    }

}
