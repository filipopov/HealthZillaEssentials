package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.BillingCard;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Order;

import java.util.List;
import java.util.Optional;

public interface BillingCardService {
    BillingCard save(BillingCard billingCard);
    Optional<BillingCard> findById(Long id);
    List<BillingCard> findAll();
}
