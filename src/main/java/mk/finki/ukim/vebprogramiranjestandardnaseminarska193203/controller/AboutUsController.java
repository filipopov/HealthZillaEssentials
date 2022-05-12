package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AboutUsController {


    @GetMapping("about-us")
    public String getAboutUsPage(Model model){

        model.addAttribute("bodyContent", "about-us");
        return "master-template";
    }
}
