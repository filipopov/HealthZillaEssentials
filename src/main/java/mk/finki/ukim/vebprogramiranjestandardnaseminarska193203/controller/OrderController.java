package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.BillingCard;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Order;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidNumberException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.BillingCardService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.OrderService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ShoppingCartService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final BillingCardService billingCardService;
    private final UserService userService;
    private Order order;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService, BillingCardService billingCardService, UserService userService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.billingCardService = billingCardService;
        this.userService = userService;
        this.order = new Order();
    }

    @PostMapping("/fillOrder")
    public String fillOrder(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String number = request.getParameter("phoneNumber");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String typePayment = request.getParameter("typepayment");
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(request.getRemoteUser());
        User user = this.userService.findByUsername(request.getRemoteUser()).get();

        try {
            this.order = new Order(name, surname, number, city, address, typePayment, shoppingCart);

            this.shoppingCartService.emptyShoppingCart(request.getRemoteUser());
            this.orderService.save(order);
            user.getOrderList().add(order);
            this.userService.save(user);
            if (typePayment.equals("Готовина"))
                model.addAttribute("bodyContent", "order-conformation");
            else
                model.addAttribute("bodyContent", "card-fill");

            return "master-template";
        }
        catch (InvalidNumberException exception){
            model.addAttribute("errorNumber", exception.getMessage());
            return "redirect:/checkout?error="+exception.getMessage();
        }
    }

    @PostMapping("/fillOrder/card")
    public String fillOrderWithCard(Model model, HttpServletRequest request) {

        String cardHolderName = request.getParameter("cardHolderName");

        Integer cardNumber = null;
        Integer expiryMonth = null;
        Integer expiryYear = null;
        Integer ccv = null;
        try {
            cardNumber = Integer.parseInt(request.getParameter("cardNumber"));
            expiryMonth = Integer.parseInt(request.getParameter("expiryMonth"));
            expiryYear = Integer.parseInt(request.getParameter("expiryYear"));
            ccv = Integer.parseInt(request.getParameter("ccv"));

        }
        catch (NumberFormatException | InvalidNumberException exception){
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Invalid Number!");
            model.addAttribute("bodyContent", "card-fill");
            return "master-template";
        }

        BillingCard billingCard = new BillingCard(cardHolderName, cardNumber, expiryMonth, expiryYear, ccv, this.order);
        this.billingCardService.save(billingCard);

        model.addAttribute("bodyContent", "order-conformation");
        return "master-template";
    }
}
