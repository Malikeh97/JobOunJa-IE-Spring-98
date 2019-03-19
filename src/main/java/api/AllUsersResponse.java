package api;

import domain.User;

import java.util.List;

public class AllUsersResponse extends SuccessResponse<List<User>> {
	public AllUsersResponse(List<User> data) {
		super(data);
	}
}
