package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "profile_picture_url")
    private String profilePictureURL;

    private String bio;
}
