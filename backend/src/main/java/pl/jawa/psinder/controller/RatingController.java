package pl.jawa.psinder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jawa.psinder.dto.RatingDto;
import pl.jawa.psinder.entity.Rating;
import pl.jawa.psinder.service.RatingService;
import pl.jawa.psinder.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }
    //all ratings every users
    @GetMapping("")
    public List<Rating> getRatings() { return ratingService.getAllRates();}
    //all ratings one user
    @GetMapping("/{from}/{id}")
    public List<Rating> getUserRatings(@PathVariable boolean from, @PathVariable long id) {
        return ratingService.getByUserId(id, from);
    }

    //sort all rates
    @GetMapping("/{rate}sort{lower}")
    public List<Rating> getSortRatings(@PathVariable int rate, @PathVariable boolean lower) {
        return ratingService.getByRates(rate, lower);
    }
    //sort one user rates
    @GetMapping("/{from}/{id}/{rate}sort{lower}")
    public List<Rating> getSortUserRatings(@PathVariable boolean from, @PathVariable long id, @PathVariable int rate, @PathVariable boolean lower) {
        List<Rating> allrates = ratingService.getByRates(rate, lower);
        if (from) {
            allrates.removeIf(r -> r.getFromUser().getId() != id);
        } else {
            allrates.removeIf(r -> r.getToUser().getId() != id);
        }
        return allrates;
    }

    //add rate
    @PostMapping("/add")
    public ResponseEntity<Rating> addRate(@RequestBody RatingDto ratingDto) {
        Rating rate = ratingService.addRate(new Rating(
                ratingDto.getRating(),
                ratingDto.getComment(),
                userService.getUserById(ratingDto.getFromUserId()).get(),
                userService.getUserById(ratingDto.getToUserId()).get()
        ));
        return ResponseEntity.ok(rate);
    }
    //update rate
    @PostMapping("/update{rateId}")
    public ResponseEntity<Rating> updateRate(@RequestBody RatingDto ratingDto, @PathVariable long rateId) {
        Rating rate = ratingService.getRateById(rateId);
        rate.setRate(ratingDto.getRating());
        rate.setComment(ratingDto.getComment());
        rate.setFromUser(userService.getUserById(ratingDto.getFromUserId()).get());
        rate.setToUser(userService.getUserById(ratingDto.getToUserId()).get());
        ratingService.updateRate(rate);
        return ResponseEntity.ok(rate);
    }
    //delete rate
    @DeleteMapping("/delete{rateId}")
    public ResponseEntity<String> deleteRate(@PathVariable long rateId) {
        ratingService.deleteRate(rateId);
        return ResponseEntity.ok("The rating has been removed. Please try again and give some feedback to the poor user.");
    }
}