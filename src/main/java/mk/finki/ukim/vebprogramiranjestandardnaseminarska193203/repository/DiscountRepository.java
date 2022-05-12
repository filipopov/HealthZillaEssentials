package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
