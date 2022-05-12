package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.CategoryService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getCategories(Model model){
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

    @GetMapping("/viewAllProducts/{id}")
    public String getProductsByCategory(@PathVariable Long id, Model model){
        model.addAttribute("category", this.categoryService.findById(id).get());
        model.addAttribute("products", this.categoryService.findById(id).get().getProducts());
        model.addAttribute("bodyContent", "view-products-by-category");
        return "master-template";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategoryPage(Model model){
        model.addAttribute("products", this.productService.findAll());
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "add-category");
        return "master-template";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam(required = false) List<Long> products,
                             Model model) {

        List<Product> productList = new ArrayList<>();
        if(products!=null)
            for (Long product : products)
                productList.add(this.productService.findById(product).get());

        Category category = new Category(name, description, productList);
        this.categoryService.save(category);

        if(products!=null)
            for (Long product : products)
                this.productService.save(product, category);

        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "redirect:/categories";
    }


    @GetMapping("/edit-form/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model){
        if(this.categoryService.findById(id).isPresent()){
            Category category = this.categoryService.findById(id).get();
            model.addAttribute("category", category);
            model.addAttribute("currProducts", this.categoryService.findById(id).get().getProducts());
            model.addAttribute("products", this.productService.findAll());
            model.addAttribute("categories", this.categoryService.findAll());
            model.addAttribute("bodyContent", "edit-category");
            return "master-template";
        }
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "redirect:/categories";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam List<Long> products,
                              Model model){

        List<Product> productList = new ArrayList<>();
        if(products!=null)
            for (Long product : products)
                productList.add(this.productService.findById(product).get());

        this.categoryService.save(id, name, description, productList);

        if(products!=null)
            for (Long product : products)
                this.productService.save(product, id);

        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "redirect:/categories";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model){
        this.categoryService.deleteById(id);
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "redirect:/categories";
    }
}
