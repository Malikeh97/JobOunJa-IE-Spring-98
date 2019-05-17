package services;

import datalayer.datamappers.endorsment.EndorsementMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import models.Endorsement;
import models.UserSkill;
import api.*;
import domain.Skill;
import domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersService {

	public String handleAllUsersRequest(HttpServletResponse response, String userNameLike) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			List<models.User> allUsers = new ArrayList<>();
			if (userNameLike == null) {
				allUsers = userMapper.findAll();
			} else {
				allUsers = userMapper.findNameLike(userNameLike);
			}
			List<User> userList = new ArrayList<>();
			models.User loggedInUser = userMapper.findById("488a14ea-faac-41d6-a870-053fd80422c7");

			if (allUsers == null) {
				ErrorResponse errorResponse = new ErrorResponse("User not found", 404);
				response.setStatus(404);
				return errorResponse.toJSON();
			}

			allUsers.remove(loggedInUser);
			for (models.User user : allUsers) {
				userList.add(new User(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getJobTitle(),
						user.getProfilePictureURL(),
						null,
						user.getBio()
				));
			}

			AllUsersResponse allUsersResponse = new AllUsersResponse(userList);
			return allUsersResponse.toJSON();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;

	}

	public String handleSingleUserRequest(HttpServletResponse response, String id) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User user = userMapper.findById(id);
			models.User loggedInUser = userMapper.findById("488a14ea-faac-41d6-a870-053fd80422c7");

			if (user == null) {
				ErrorResponse errorResponse = new ErrorResponse("User not found", 404);
				response.setStatus(404);
				return errorResponse.toJSON();
			}

			List<models.Skill> userSkillsModel = userSkillMapper.findUserSkillById(user.getId());
			List<Skill> userSkillsDomain = new ArrayList<>();
			for (models.Skill skill : userSkillsModel) {
				Skill newSkill = new Skill();
				newSkill.setName(skill.getName());
				int point = endorsementMapper.countNumOfEndorsements(skill.getId(), user.getId());
				List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), id);
				newSkill.setPoint(point);
				newSkill.setEndorsers(endorserIdList);
				userSkillsDomain.add(newSkill);

			}

			User newUser = new User(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getJobTitle(),
					user.getProfilePictureURL(),
					userSkillsDomain,
					user.getBio()
			);

			if (user == loggedInUser) {
				UserProfileResponse userProfileResponse = new UserProfileResponse(newUser);
				return userProfileResponse.toJSON();
			} else { // may be changing it later
				UserProfileResponse userProfileResponse = new UserProfileResponse(newUser);
				return userProfileResponse.toJSON();
			}

		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;

	}

	public String handleEndorseRequest(SkillRequest request, HttpServletResponse response, String id, HttpServletRequest servletRequest) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();

			User user = userMapper.findByIdWithSkills(id);
			models.User loggedInUser = (models.User)servletRequest.getAttribute("user");

			if (id.equals(loggedInUser.getId())) {
				FailResponse<String> failResponse = new FailResponse<>("You cannot endorse skill of yours");
				response.setStatus(403);
				return failResponse.toJSON();
			}

			boolean found = false;
			for (Skill skill : user.getSkills()) {
				if (skill.getName().equals(request.getSkill())) {
					if (EndorsementMapper.isEndorsedByUserId(loggedInUser.getId(), skill.getId(), user.getId())) {
						FailResponse<String> failResponse = new FailResponse<>("You cannot endorse a skill twice");
						response.setStatus(404);
						return failResponse.toJSON();
					}

					endorsementMapper.save(new Endorsement(UUID.randomUUID().toString(), loggedInUser.getId(), skill.getId(), user.getId()));
					found = true;
					break;
				}
			}

			if (!found) {
				FailResponse<String> failResponse = new FailResponse<>("This user doesn't have this skill");
				response.setStatus(404);
				return failResponse.toJSON();
			}

			for (Skill skill : user.getSkills()) {
				List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), loggedInUser.getId());
				skill.setEndorsers(endorserIdList);
				skill.setPoint(endorserIdList.size());
			}
			UserProfileResponse userProfileResponse = new UserProfileResponse(new User(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getJobTitle(),
					user.getProfilePictureURL(),
					user.getSkills(),
					user.getBio()));
			return userProfileResponse.toJSON();

		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public String handleAddSkillRequest(SkillRequest request, HttpServletResponse response, HttpServletRequest servletRequest) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User user = (models.User) servletRequest.getAttribute("user");
			User loggedInUser = userMapper.findByIdWithSkills(user.getId());

			if (loggedInUser.getSkills().stream()
					.filter(skill -> skill.getName().equals(request.getSkill()))
					.findFirst()
					.orElse(null) != null) {
				FailResponse<String> failResponse = new FailResponse<>("You already have this skill");
				response.setStatus(400);
				return failResponse.toJSON();
			}

			models.Skill newSkillModel = SkillMapper.findByName(request.getSkill());
			if (newSkillModel == null) {
				FailResponse<String> failResponse = new FailResponse<>("Unknown skill");
				response.setStatus(400);
				return failResponse.toJSON();
			}
			userSkillMapper.save(new UserSkill(UUID.randomUUID().toString(), loggedInUser.getId(), newSkillModel.getId()));

			for (Skill skill : loggedInUser.getSkills()) {
				List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), loggedInUser.getId());
				skill.setEndorsers(endorserIdList);
				skill.setPoint(endorserIdList.size());
			}
			UserProfileResponse userProfileResponse = new UserProfileResponse(new User(loggedInUser.getId(),
					loggedInUser.getFirstName(),
					loggedInUser.getLastName(),
					loggedInUser.getJobTitle(),
					loggedInUser.getProfilePictureURL(),
					loggedInUser.getSkills(),
					loggedInUser.getBio()));
			return userProfileResponse.toJSON();

		} catch (SQLException e) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			response.setStatus(500);
			return errorResponse.toJSON();
		}
	}

	public String handleDeleteRequest(SkillRequest request, HttpServletResponse response, HttpServletRequest servletRequest) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User user = (models.User) servletRequest.getAttribute("user");
			User loggedInUser = userMapper.findByIdWithSkills(user.getId());
			for (Skill skill : loggedInUser.getSkills()) {
				if (skill.getName().equals(request.getSkill())) {
					userSkillMapper.deleteById(skill.getId());
					loggedInUser.getSkills().remove(skill);
					break;
				}
			}

			for (Skill skill : loggedInUser.getSkills()) {
				List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), loggedInUser.getId());
				skill.setEndorsers(endorserIdList);
				skill.setPoint(endorserIdList.size());
			}
			UserProfileResponse userProfileResponse = new UserProfileResponse(new User(loggedInUser.getId(),
					loggedInUser.getFirstName(),
					loggedInUser.getLastName(),
					loggedInUser.getJobTitle(),
					loggedInUser.getProfilePictureURL(),
					loggedInUser.getSkills(),
					loggedInUser.getBio()));
			return userProfileResponse.toJSON();

		} catch (SQLException e) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			response.setStatus(500);
			return errorResponse.toJSON();
		}
	}
}
