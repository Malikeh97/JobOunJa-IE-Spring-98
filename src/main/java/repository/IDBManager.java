package repository;

import domain.Project;
import domain.Skill;
import domain.User;

import java.util.List;

public interface IDBManager {
    public User findUserById(String id);
    public List<Project> findAllProjects();
    public List<Skill> findAllSkills();
    public List<User> findAllUsers();
    public Project findProjectById(String id);
    public void addUser(User user);
}
