package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountService {
    Discount save(Discount discount);
    List<Discount> findAll();
    Optional<Discount> findById(Long id);
}
