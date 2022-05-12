package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.*;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.NoCategoryFoundException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final ShoppingCartService shoppingCartService;
    private final DiscountService discountService;

    public ProductsController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService, ShoppingCartService shoppingCartService, DiscountService discountService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.shoppingCartService = shoppingCartService;
        this.discountService = discountService;
    }

    @GetMapping
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.findAllAvailable());
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model, HttpServletRequest request){
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(request.getRemoteUser());
        this.shoppingCartService.getProductByIdFromActiveShoppingCart(id, request.getRemoteUser());
        this.productService.deleteById(id);
        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "redirect:/products";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model){
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model){
        if(this.productService.findById(id).isPresent()){
            Product product = this.productService.findById(id).get();
            model.addAttribute("categories", this.categoryService.findAll());
            model.addAttribute("manufacturers", this.manufacturerService.findAll());
            model.addAttribute("product", product);
            model.addAttribute("products", this.productService.findAll());
            model.addAttribute("bodyContent", "edit-product");
            return "master-template";
        }
        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String search, Model model){
//        model.addAttribute("products", this.productService.findAllByNameLike(search));
        model.addAttribute("products", this.productService.search(search));
        model.addAttribute("bodyContent", "products");
        return "master-template";

//        Postoi problem so Hibernate verziite 5.6.6 i 5.6.7 spored spring data jpa issue #2472
//        problemot e HHH-15142.
//        Dokolku koristite nekoja od predhodno-navedenite verzii potrebno e da se downgrade-ne na Hibernate 5.6.5 so cel da se izbegnat problemi so ovaa funkcionalnost.
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Long category,
                              @RequestParam Integer price,
                              @RequestParam Integer availablePieces,
                              @RequestParam Long manufacturer,
                              Model model){

        Category category1 = this.categoryService.findById(category).get();
        Manufacturer manufacturer1 = this.manufacturerService.findById(manufacturer).get();
        this.productService.save(id, name, description, category1, price, availablePieces, manufacturer1);


        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam(required = false) List<Long> categories,
                             @RequestParam String price,
                             @RequestParam String availablePieces,
                             @RequestParam Long manufacturer,
                             Model model){

        List<Category> categoryList = new ArrayList<>();

        if(categories!=null) {
            for (Long category : categories)
                categoryList.add(this.categoryService.findById(category).get());
        }
        else{
            model.addAttribute("hasErrorCategory", true);
            model.addAttribute("categories", this.categoryService.findAll());
            model.addAttribute("manufacturers", this.manufacturerService.findAll());
            Product product = new Product(name, description, new Category(), Integer.parseInt(price), Integer.parseInt(availablePieces), this.manufacturerService.findById(manufacturer).get());
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }

        try{
            Integer.parseInt(price);
            Integer.parseInt(availablePieces);
        }
        catch (NumberFormatException exception){
            model.addAttribute("hasErrorInteger", true);
            model.addAttribute("categories", this.categoryService.findAll());
            model.addAttribute("manufacturers", this.manufacturerService.findAll());
            Product product = new Product(name, description, new Category(), 0, 1, this.manufacturerService.findById(manufacturer).get());
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }

        Manufacturer manufacturer1 = this.manufacturerService.findById(manufacturer).get();
        this.productService.save(new Product(name, description, categoryList, Integer.parseInt(price), Integer.parseInt(availablePieces), manufacturer1));
        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "redirect:/products";
    }

    @GetMapping("/discount/{id}")
    public String addDiscount(@PathVariable Long id, Model model){
        model.addAttribute("product", this.productService.findById(id).get());
        model.addAttribute("bodyContent", "add-discount");
        return "master-template";
    }

    @PostMapping("/discount/add/{id}")
    public String addDiscountToProduct(@PathVariable Long id, @RequestParam Integer percentage,Model model){
        Product product = this.productService.findById(id).get();
        Discount discount = new Discount(percentage);
        this.discountService.save(discount);
        Integer price = product.getPrice();
        Integer discountedPrice = price - ((price/10)*(percentage/10));
        product.setDiscountedPrice(discountedPrice);
        product.setDiscount(discount);

        this.productService.save(product);

        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "redirect:/products";
    }
}
