package pl.jawa.psinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jawa.psinder.entity.User;

@Data
@AllArgsConstructor
public class RatingDto {
    private int rating;
    private String comment;
    private long fromUserId;
    private long toUserId;
}
