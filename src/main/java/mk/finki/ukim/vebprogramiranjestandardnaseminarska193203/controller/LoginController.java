package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.AuthService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl.EmailSenderService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    public LoginController(AuthService authService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getLoginPage(){
        return "newLogin";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model){
        User user = null;
        try{
            user = this.authService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/home";
        }
        catch (InvalidUserCredentialsException | InvalidArgumentsException exception){
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "newLogin";
        }
    }

    @GetMapping("/forgotPassword")
    public String getForgotPasswordForm(){
        return "forgotPassword-form";
    }

    @PostMapping("/getPassword")
    public String getPassword(HttpServletRequest request, Model model){
        Optional<User> user = this.userService.findByUsername(request.getParameter("username"));
        if(user.isPresent()){
            String newPassword = RandomString.make();
            this.userService.findByUsername(request.getParameter("username")).get().setPassword(passwordEncoder.encode(newPassword));
            this.userService.save(this.userService.findByUsername(request.getParameter("username")).get());
            this.emailSenderService.sendEmail(user.get().getEmail(),
                    "Заборавена лозинка", "Искористете ја следната генерирана лозинка за да пристапите до Вашиот профил: "+ newPassword +" Одкако ќе пристапите до Вашиот профил, можете да ја промените лозинката навигирајќи до полето 'Мој Профил'.");
            model.addAttribute("success", "Ви испративме меил во кој се поставени инструкции за тоа како да го обезбедите Вашиот профил.");
        }
        else {
            model.addAttribute("hasError", true);
            model.addAttribute("errorMessage", "Непостоечко корисничко име");
        }
        return "forgotPassword-form";

    }
}
