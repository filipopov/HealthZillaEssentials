package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.Role;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.UserWithUsernameAlreadyExistsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.AuthService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "newLogin";
    }

    @PostMapping
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String repeatPassword,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email){

        try {
            this.userService.register(username, password, repeatPassword, name, surname, email);
            return "redirect:/login";
        }
        catch (InvalidUserCredentialsException | InvalidArgumentsException | UserWithUsernameAlreadyExistsException | PasswordsDoNotMatchException exception){
            return "redirect:/register?error="+exception.getMessage();
        }
    }
}
