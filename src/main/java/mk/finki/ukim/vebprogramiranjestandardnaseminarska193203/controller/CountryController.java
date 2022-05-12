package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.CountryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public String getCountriesPage(Model model){
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("bodyContent", "countries");
        return "master-template";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCountryPage(Model model){
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("bodyContent", "add-country");
        return "master-template";
    }

    @PostMapping("/add")
    public String addCountry(@RequestParam String name,
                             @RequestParam String city,
                             @RequestParam String address,
                             Model model) {

        this.countryService.save(new Country(name, city, address));
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("bodyContent", "countries");
        return "redirect:/countries";
    }


    @GetMapping("/edit-form/{id}")
    public String editCountryPage(@PathVariable Long id, Model model){
        if(this.countryService.findById(id).isPresent()){
            Country country = this.countryService.findById(id).get();
            model.addAttribute("country", country);
            model.addAttribute("countries", this.countryService.findAll());
            model.addAttribute("bodyContent", "edit-country");
            return "master-template";
        }
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("bodyContent", "countries");
        return "redirect:/countries";
    }

    @PostMapping("/edit/{id}")
    public String editCountry(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String city,
                              @RequestParam String address,
                              Model model){

        this.countryService.save(id, name, city, address);

        model.addAttribute("products", this.countryService.findAll());
        model.addAttribute("bodyContent", "countries");
        return "redirect:/countries";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCountry(@PathVariable Long id, Model model){
        this.countryService.deleteById(id);
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("bodyContent", "countries");
        return "redirect:/countries";
    }
}
