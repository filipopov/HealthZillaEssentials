package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Discount;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.DiscountRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount save(Discount discount) {
        return this.discountRepository.save(discount);
    }

    @Override
    public List<Discount> findAll() {
        return this.discountRepository.findAll();
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return this.discountRepository.findById(id);
    }
}
