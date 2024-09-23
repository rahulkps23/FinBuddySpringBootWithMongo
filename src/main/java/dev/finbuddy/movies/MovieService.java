package dev.finbuddy.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public List<Movie> findAllMovies() {
        return repository.findAll();
    }
    public Optional<Movie> findMovieByImdbId(String imdbId) {
        return repository.findMovieByImdbId(imdbId);
//        Optional<Movie> movie = repository.findMovieByImdbId(imdbId);
//
//        movie.ifPresent(m -> {
//            List<ObjectId> reviewIds = m.getReviewIds();
//            List<Review> reviews = reviewRepository.findAllById(reviewIds);
//            m.setReviews(reviews); // Set reviews in the Movie object
//        });
    }
}