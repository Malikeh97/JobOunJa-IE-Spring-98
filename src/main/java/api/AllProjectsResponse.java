package api;

import domain.Project;

import java.util.List;

public class AllProjectsResponse extends SuccessResponse<List<Project>> {
    public AllProjectsResponse(List<Project> data) {
        super(data);
    }
}
