package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ProductService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    public CheckoutController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
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
        model.addAttribute("total", this.shoppingCartService.getTotal(request.getRemoteUser()));
        model.addAttribute("delivery", this.shoppingCartService.getTotal(request.getRemoteUser())>=2000?0:150);
        model.addAttribute("bodyContent", "checkout");
        return "checkout";
    }
}
