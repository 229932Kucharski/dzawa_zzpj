package pl.jawa.psinder.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private boolean verified;

    private long id;

    public UserDto(long id, String username, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.verified = false;
    }
}
