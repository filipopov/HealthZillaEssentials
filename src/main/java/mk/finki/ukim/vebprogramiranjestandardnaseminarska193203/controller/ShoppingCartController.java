package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest request, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = request.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("products", this.shoppingCartService
                .listAllProductsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";

    }

    @PostMapping("/add-product/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest request, Model model){
        try{
//            User user = (User) request.getSession().getAttribute("user");
            String username = request.getRemoteUser();
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(username, id);
            return "redirect:/shopping-cart";
        }
        catch (RuntimeException exception){
            return "redirect:/shopping-cart?error="+exception.getMessage();
        }

    }

}
