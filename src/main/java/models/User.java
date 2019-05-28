package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;
import utils.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(name = "user_name", nullable = false, unique = true, length = 40)
    private String userName;

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;

    @Column(name = "profile_picture_url", nullable = false)
    private String profilePictureURL;

    @Column(nullable = false)
    private String bio;
}
