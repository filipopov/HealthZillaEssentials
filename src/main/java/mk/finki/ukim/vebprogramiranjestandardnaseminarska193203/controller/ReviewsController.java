package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;


import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    private final UserService userService;

    public ReviewsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllReviews(Model model, HttpServletRequest request){
        User user = this.userService.findByUsername(request.getRemoteUser()).get();

        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("bodyContent", "reviews");
        return "master-template";
    }
}
