package datalayer.datamappers.project;

import datalayer.datamappers.IMapper;
import models.Project;
import models.Skill;

import java.sql.SQLException;
import java.util.List;

public interface IProjectMapper extends IMapper<Project, String> {
	public Long maxCreationDate() throws SQLException;

	public List<domain.Project> findAllForDomain() throws SQLException;

	public domain.Project findByIdForDomain(String id) throws SQLException;
}
