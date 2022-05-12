package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.CountryRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country save(Country country) {
        return this.countryRepository.save(country);
    }

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Country save(Long id, String name, String city, String address) {
        if(this.countryRepository.findById(id).isPresent()) {
            Country country = this.countryRepository.getById(id);
            country.setName(name);
            country.setCity(city);
            country.setAddress(address);

            return this.countryRepository.save(country);
        }
        return this.countryRepository.save(new Country(name, city, address));
    }

    @Override
    public Optional<Country> findById(Long id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.countryRepository.deleteById(id);
    }
}
