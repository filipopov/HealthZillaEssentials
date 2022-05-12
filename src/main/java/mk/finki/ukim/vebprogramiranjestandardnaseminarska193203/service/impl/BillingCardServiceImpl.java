package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.BillingCard;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Order;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.BillingCardRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.BillingCardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingCardServiceImpl implements BillingCardService {

    private final BillingCardRepository billingCardRepository;

    public BillingCardServiceImpl(BillingCardRepository billingCardRepository) {
        this.billingCardRepository = billingCardRepository;
    }

    @Override
    public BillingCard save(BillingCard billingCard) {
        return this.billingCardRepository.save(billingCard);
    }

    @Override
    public Optional<BillingCard> findById(Long id) {
        return this.billingCardRepository.findById(id);
    }

    @Override
    public List<BillingCard> findAll() {
        return this.billingCardRepository.findAll();
    }
}
