package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
    Optional<Manufacturer> findById(Long id);
    void deleteById(Long id);
    Manufacturer save(Long id, String name, Country country);
}
