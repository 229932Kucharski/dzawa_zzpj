package pl.jawa.psinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jawa.psinder.entity.Rating;
import pl.jawa.psinder.repository.RatingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public List<Rating> getAllRates() {
        return ratingRepository.findAll();
    }

    public Rating getRateById(long id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> getByUserId(long id, boolean isSender) {
        if(isSender) {
            return ratingRepository.findByFromUserId(id);
        } else {
            return ratingRepository.findByToUserId(id);
        }
    }

    public List<Rating> getByRates(int rate, boolean isLower) {
        if(isLower) {
            return ratingRepository.findWorseOrExactRates(rate);
        } else {
            return ratingRepository.findBetterOrExactRates(rate);
        }
    }

    public Rating addRate(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating updateRate(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void deleteRate(long id) {
        ratingRepository.deleteById(id);
    }
}
