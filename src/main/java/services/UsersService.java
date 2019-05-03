package services;

import repository.InMemoryDBManager;
import api.*;
import domain.Skill;
import domain.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersService {
	public String handleAllUsersRequest(HttpServletResponse response) throws IOException {
		List<User> allUsers = InMemoryDBManager.shared.findAllUsers();
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (allUsers == null || allUsers.size() < 2) {
			ErrorResponse errorResponse = new ErrorResponse("User not found", 404);
			response.setStatus(404);
			return errorResponse.toJSON();
		}
		allUsers.remove(loggedInUser);
		AllUsersResponse allUsersResponse = new AllUsersResponse(allUsers);
		return allUsersResponse.toJSON();
	}

	public String handleSingleUserRequest(HttpServletResponse response, String id) throws IOException {
		User user = InMemoryDBManager.shared.findUserById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (user == null) {
			ErrorResponse errorResponse = new ErrorResponse("User not found", 404);
			response.setStatus(404);
			return errorResponse.toJSON();
		}
		if (user == loggedInUser) {
//			List<Skill> skills = InMemoryDBManager.shared.findAllSkills();
			UserProfileResponse userProfileResponse = new UserProfileResponse(user);
			return userProfileResponse.toJSON();
		} else { // may be changing it later
			UserProfileResponse userProfileResponse = new UserProfileResponse(user);
			return userProfileResponse.toJSON();
		}
	}

	public String handleEndorseRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		User user = InMemoryDBManager.shared.findUserById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (id.equals(loggedInUser.getId())) {
			FailResponse failResponse = new FailResponse("You cannot endorse skill of yours");
			response.setStatus(403);
			return failResponse.toJSON();
		}
		boolean found = false;
		for (Skill skill: user.getSkills()) {
			if(skill.getName().equals(request.getSkill())) {
				if (skill.getEndorsers().stream().filter(endorser -> endorser.equals(loggedInUser.getId())).findFirst().orElse(null) != null) {
					FailResponse failResponse = new FailResponse("You cannot endorse a skill twice");
					response.setStatus(404);
					return failResponse.toJSON();
				}
				skill.getEndorsers().add(loggedInUser.getId());
				skill.setPoint(skill.getPoint() + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			FailResponse failResponse = new FailResponse("This user doesn't have this skill");
			response.setStatus(404);
			return failResponse.toJSON();
		}
		UserProfileResponse userProfileResponse = new UserProfileResponse(user);
		return userProfileResponse.toJSON();
	}

	public String handleAddSkillRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		List<Skill> skills = InMemoryDBManager.shared.findAllSkills();
		if (skills.stream().filter(skill -> skill.getName().equals(request.getSkill())).findFirst().orElse(null) == null) {
			FailResponse failResponse = new FailResponse("Skill isn't available");
			response.setStatus(400);
			return failResponse.toJSON();
		}
		boolean isRepeated = false;
		if(id.equals(loggedInUser.getId())) {
			for (Skill skill: loggedInUser.getSkills()) {
				if((skill.getName()).equals(request.getSkill())) {
					isRepeated = true;
					break;
				}
			}
			if (!isRepeated) {
				Skill newSkill = new Skill(request.getSkill(), 0, new ArrayList<>());
				loggedInUser.getSkills().add(newSkill);
				UserProfileResponse userProfileResponse = new UserProfileResponse(loggedInUser);
				return userProfileResponse.toJSON();
			} else {
				FailResponse failResponse = new FailResponse("You already have this skill");
				response.setStatus(400);
				return failResponse.toJSON();
			}
		} else {
			FailResponse failResponse = new FailResponse("You cannot add skill for others");
			response.setStatus(403);
			return failResponse.toJSON();
		}
	}

	public String handleDeleteRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if(id.equals(loggedInUser.getId())) {
			for (Skill skill: loggedInUser.getSkills()) {
				if((skill.getName()).equals(request.getSkill())) {
					loggedInUser.getSkills().remove(skill);
					break;
				}
			}
			UserProfileResponse userProfileResponse = new UserProfileResponse(loggedInUser);
			return userProfileResponse.toJSON();
		} else {
			FailResponse failResponse = new FailResponse("You cannot remove skill from others");
			response.setStatus(403);
			return failResponse.toJSON();
		}
	}
}
