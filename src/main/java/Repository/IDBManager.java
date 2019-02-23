package Repository;

import domain.Project;
import domain.User;

import java.util.List;

public interface IDBManager {
    public User findUserById(String id);
    public List<Project> findAllProjects();
    public Project findProjectById(String id);
}
