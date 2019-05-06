package datalayer.datamappers.project;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import datalayer.datamappers.bid.BidMapper;
import datalayer.datamappers.projectskill.ProjectSkillMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.user.UserMapper;
import models.Project;
import utils.MapperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {
	public static final String TABLE_NAME = "projects";

	public ProjectMapper() throws SQLException {
		super(Project.class, TABLE_NAME);
	}

	@Override
	public Long maxCreationDate() throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getMaxStatement("creation_date"));
		) {
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		}
	}

	@Override
	public List<domain.Project> findAllForDomain() throws SQLException {
		Map<String, domain.Project> projectMap = new HashMap<>();
		List<domain.Project> projectList = new ArrayList<>();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getProjectsWithWinnerStatement(false))
		) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				domain.Project project = new domain.Project();
				project.setId(rs.getString(1));
				project.setTitle(rs.getString(2));
				project.setDescription(rs.getString(3));
				project.setImageURL(rs.getString(4));
				project.setBudget(rs.getInt(5));
				project.setDeadline(rs.getLong(6));
				project.setCreationDate(rs.getLong(7));
				String winnerId = rs.getString(8);
				if (!winnerId.equals("null")) {
					domain.User winner = new domain.User();
					winner.setId(winnerId);
					winner.setFirstName(rs.getString(9));
					winner.setLastName(rs.getString(10));
					project.setWinner(winner);
				}
				project.setSkills(new ArrayList<>());
				project.setBids(new ArrayList<>());
				projectMap.put(project.getId(), project);
				projectList.add(project);
			}
		}

		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getProjectsWithSkillsStatement(false))
		) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String projectId = rs.getString(1);
				domain.Project project = projectMap.get(projectId);
				domain.Skill skill = new domain.Skill();
				skill.setId(rs.getString(2));
				skill.setName(rs.getString(3));
				skill.setPoint(rs.getInt(4));
				project.getSkills().add(skill);
			}
		}

		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getProjectsWithBidsStatement(false))
		) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String projectId = rs.getString(1);
				domain.Project project = projectMap.get(projectId);
				domain.Bid bid = new domain.Bid();
				bid.setId(rs.getString(2));
				bid.setUserId(rs.getString(3));
				bid.setBidAmount(rs.getInt(4));
				bid.setProjectId(projectId);
				project.getBids().add(bid);
			}
		}

		return projectList;
	}

	private String getProjectsWithWinnerStatement(boolean single) {
		return String.format("SELECT p.id, p.title, p.description, p.image_url, p.budget, p.deadline, p.creation_date, " +
						"p.winner_id, u.first_name, u.last_name " +
						"FROM %s p " +
						"LEFT JOIN %s u ON p.winner_id = u.id %s",
				ProjectMapper.TABLE_NAME,
				UserMapper.TABLE_NAME,
				single ? "WHERE id = ?" : "ORDER BY p.creation_date DESC"
		);
	}

	private String getProjectsWithSkillsStatement(boolean single) {
		return String.format("SELECT p.id, s.id as skill_id, s.name, ps.point " +
						"FROM %s p " +
						"JOIN %s ps ON p.id = ps.project_id " +
						"JOIN %s s ON ps.skill_id = s.id %s",
				ProjectMapper.TABLE_NAME,
				ProjectSkillMapper.TABLE_NAME,
				SkillMapper.TABLE_NAME,
				single ? "WHERE id = ?" : ""
		);
	}

	private String getProjectsWithBidsStatement(boolean single) {
		return String.format("SELECT p.id, b.id as bid_id, b.user_id, b.amount " +
						"FROM %s p " +
						"JOIN %s b ON p.id = b.project_id %s",
				ProjectMapper.TABLE_NAME,
				BidMapper.TABLE_NAME,
				single ? "WHERE id = ?" : ""
		);
	}


}
