package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.BillingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingCardRepository extends JpaRepository<BillingCard, Long> {
}
