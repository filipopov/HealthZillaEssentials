package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAll();
    Review save(Review review);
}
