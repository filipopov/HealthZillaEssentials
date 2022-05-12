package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Country save(Country country);
    List<Country> findAll();
    Country save(Long id, String name, String city, String address);
    Optional<Country> findById(Long id);
    void deleteById(Long id);
}
