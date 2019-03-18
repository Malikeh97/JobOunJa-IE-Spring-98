package api;

import domain.User;

public class UserProfileResponse extends SuccessResponse<User> {
	public UserProfileResponse(User data) {
		super(data);
	}
}
