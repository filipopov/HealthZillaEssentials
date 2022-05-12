package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Country;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ManufacturerRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return this.manufacturerRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.manufacturerRepository.deleteById(id);
    }

    @Override
    public Manufacturer save(Long id, String name, Country country) {
        if(this.manufacturerRepository.findById(id).isPresent()) {
            Manufacturer manufacturer = this.manufacturerRepository.getById(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return this.manufacturerRepository.save(manufacturer);
        }
        return this.manufacturerRepository.save(new Manufacturer(name, country));
    }
}
