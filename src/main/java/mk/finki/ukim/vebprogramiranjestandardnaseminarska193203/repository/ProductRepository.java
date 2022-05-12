package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameLike(String name);
}
