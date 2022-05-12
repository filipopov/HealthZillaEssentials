package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
