package api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
}
