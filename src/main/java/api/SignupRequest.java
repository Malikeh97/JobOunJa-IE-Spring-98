package api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String name;
    private String familyName;
    private String username;
    private String password;
    private String jobTitle;
    private String image;
    private String bio;
}
