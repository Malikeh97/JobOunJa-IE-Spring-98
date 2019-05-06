package services;

import datalayer.datamappers.endorsment.EndorsementMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import models.Endorsement;
import models.UserSkill;
import repository.InMemoryDBManager;
import api.*;
import domain.Skill;
import domain.User;
import utils.Column;
import utils.ForeignKey;
import utils.Id;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UsersService {

	public String handleAllUsersRequest(HttpServletResponse response, String userNameLike) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			List<models.User> allUsers = new ArrayList<>();
			if(userNameLike == null) {
				allUsers = userMapper.findAll();
			}
			else {
				allUsers = userMapper.findNameLike(userNameLike);
			}
			List<User> userList = new ArrayList<>();
			models.User loggedInUser = userMapper.findById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");

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
		try{
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User user = userMapper.findById(id);
			models.User loggedInUser = userMapper.findById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");

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

	public String handleEndorseRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();

			models.User user = userMapper.findById(id);
			models.User loggedInUser = userMapper.findById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");

			if (id.equals(loggedInUser.getId())) {
				FailResponse failResponse = new FailResponse("You cannot endorse skill of yours");
				response.setStatus(403);
				return failResponse.toJSON();
			}

			boolean found = false;

			List<models.Skill> userSkillsModel = userSkillMapper.findUserSkillById(user.getId());
			List<User> endorserList = new ArrayList<>();

			for (models.Skill skill: userSkillsModel) {
				if(skill.getName().equals(request.getSkill())) {

					if (EndorsementMapper.isEndorsedByUserId(loggedInUser.getId(), skill.getId(), user.getId())) {
						FailResponse failResponse = new FailResponse("You cannot endorse a skill twice");
						response.setStatus(404);
						return failResponse.toJSON();
					}

					endorsementMapper.save(new Endorsement(UUID.randomUUID().toString(), loggedInUser.getId(), skill.getId(), user.getId()));
					found = true;
					break;
				}
			}

			if (!found) {
				FailResponse failResponse = new FailResponse("This user doesn't have this skill");
				response.setStatus(404);
				return failResponse.toJSON();
			}

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

			UserProfileResponse userProfileResponse = new UserProfileResponse(new User(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getJobTitle(),
					user.getProfilePictureURL(),
					userSkillsDomain,
					user.getBio()));

			return userProfileResponse.toJSON();

		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public String handleAddSkillRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			SkillMapper skillMapper = new SkillMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User loggedInUser = userMapper.findById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");

			List<models.Skill> skills = skillMapper.findAll();

			if (skills.stream().filter(skill -> skill.getName().equals(request.getSkill())).findFirst().orElse(null) == null) {
				FailResponse failResponse = new FailResponse("Skill isn't available");
				response.setStatus(400);
				return failResponse.toJSON();
			}

			boolean isRepeated = false;
			if(id.equals(loggedInUser.getId())) {
				List<models.Skill> userSkillsModel = userSkillMapper.findUserSkillById(loggedInUser.getId());
				for (models.Skill skill: userSkillsModel) {
					if((skill.getName()).equals(request.getSkill())) {
						isRepeated = true;
						break;
					}
				}

				if (!isRepeated) {
					models.Skill newSkill = SkillMapper.findByName(request.getSkill());
					userSkillMapper.save(new UserSkill(UUID.randomUUID().toString(), loggedInUser.getId(), newSkill.getId()));

					List<Skill> userSkillsDomain = new ArrayList<>();
					userSkillsModel = userSkillMapper.findUserSkillById(loggedInUser.getId());
					for (models.Skill skill : userSkillsModel) {
						Skill itsSkill = new Skill();
						itsSkill.setName(skill.getName());
						int point = endorsementMapper.countNumOfEndorsements(skill.getId(), loggedInUser.getId());
						List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), id);
						itsSkill.setEndorsers(endorserIdList);
						itsSkill.setPoint(point);
						userSkillsDomain.add(itsSkill);
					}
					UserProfileResponse userProfileResponse = new UserProfileResponse(new User(loggedInUser.getId(),
							loggedInUser.getFirstName(),
							loggedInUser.getLastName(),
							loggedInUser.getJobTitle(),
							loggedInUser.getProfilePictureURL(),
							userSkillsDomain,
							loggedInUser.getBio()));
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

		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public String handleDeleteRequest(SkillRequest request, HttpServletResponse response, String id) throws IOException {
		try {
			UserMapper userMapper = new UserMapper();
			UserSkillMapper userSkillMapper = new UserSkillMapper();
			EndorsementMapper endorsementMapper = new EndorsementMapper();
			models.User loggedInUser = userMapper.findById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");
			if(id.equals(loggedInUser.getId())) {
				List<models.Skill> userSkillsModel = userSkillMapper.findUserSkillById(loggedInUser.getId());
				for (models.Skill skill: userSkillsModel) {
					if((skill.getName()).equals(request.getSkill())) {
						String deleteId = userSkillMapper.findUserSkillId(loggedInUser.getId(), skill.getId());
						userSkillMapper.deleteById(deleteId);
						break;
					}
				}

				List<Skill> userSkillsDomain = new ArrayList<>();
				userSkillsModel = userSkillMapper.findUserSkillById(loggedInUser.getId());
				for (models.Skill skill : userSkillsModel) {
					Skill itsSkill = new Skill();
					itsSkill.setName(skill.getName());
					int point = endorsementMapper.countNumOfEndorsements(skill.getId(), loggedInUser.getId());
					List<String> endorserIdList = endorsementMapper.findEndorserIdList(skill.getId(), id);
					itsSkill.setEndorsers(endorserIdList);
					itsSkill.setPoint(point);
					userSkillsDomain.add(itsSkill);
				}
				UserProfileResponse userProfileResponse = new UserProfileResponse(new User(loggedInUser.getId(),
						loggedInUser.getFirstName(),
						loggedInUser.getLastName(),
						loggedInUser.getJobTitle(),
						loggedInUser.getProfilePictureURL(),
						userSkillsDomain,
						loggedInUser.getBio()));
				return userProfileResponse.toJSON();

			} else {
				FailResponse failResponse = new FailResponse("You cannot remove skill from others");
				response.setStatus(403);
				return failResponse.toJSON();
			}

		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;

	}
}
