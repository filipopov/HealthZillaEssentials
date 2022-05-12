package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.controller;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.CountryService;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    private final CountryService countryService;

    public ManufacturerController(ManufacturerService manufacturerService, CountryService countryService) {
        this.manufacturerService = manufacturerService;
        this.countryService = countryService;
    }

    @GetMapping
    public String getManufacturersPage(Model model){
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "master-template";
    }


    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addManufacturersPage(Model model){
        model.addAttribute("countries", this.countryService.findAll());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "add-manufacturer");
        return "master-template";
    }

    @PostMapping("/add")
    public String addManufacturer(@RequestParam String name,
                             @RequestParam Long countryId,
                             Model model){
        Country country = this.countryService.findById(countryId).get();
        this.manufacturerService.save(new Manufacturer(name, country));
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "redirect:/manufacturers";
    }

    @GetMapping("/edit-form/{id}")
    public String editManufacturerPage(@PathVariable Long id, Model model){
        if(this.manufacturerService.findById(id).isPresent()){
            Manufacturer manufacturer = this.manufacturerService.findById(id).get();
            model.addAttribute("countries", this.countryService.findAll());
            model.addAttribute("manufacturer", manufacturer);
            model.addAttribute("manufacturers", this.manufacturerService.findAll());
            model.addAttribute("bodyContent", "edit-manufacturer");
            return "master-template";
        }
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "redirect:/manufacturers";
    }

    @PostMapping("/edit/{id}")
    public String editManufacturer(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam Long countryId,
                              Model model){

        Country country = this.countryService.findById(countryId).get();
        this.manufacturerService.save(id, name, country);


        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "redirect:/manufacturers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id, Model model){
        this.manufacturerService.deleteById(id);
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "redirect:/manufacturers";
    }
}
