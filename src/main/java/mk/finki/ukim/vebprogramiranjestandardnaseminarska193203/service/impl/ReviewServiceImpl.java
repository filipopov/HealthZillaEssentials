package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Review;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ReviewRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAll() {
        return this.reviewRepository.findAll();
    }

    @Override
    public Review save(Review review) {
        return this.reviewRepository.save(review);
    }
}
