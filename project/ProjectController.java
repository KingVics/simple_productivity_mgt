package project;

import java.util.ArrayList;
import java.util.List;

import common.FileDatabaseHelper;
import common.createLogic;

// Project controller exposing methods to manage project
public class ProjectController implements createLogic<Project> {

    // save project to database
    @Override
    public Project create(Project newProject) {
        FileDatabaseHelper.appendLine(newProject.toFileString());
        return newProject;
    }

    // only return project by createdBy
    public List<Project> getAllProjects(String userId) {
        List<Project> userProjects = new ArrayList<>();
        List<String> lines = FileDatabaseHelper.readAllLines();

        for (String line : lines) {
            if (line.startsWith("PROJECT:")) {
                Project p = new Project().fromFileString(line);
                if (p != null && p.getCreatedBy().equals(userId)) {
                    userProjects.add(p);
                }
            }
        }
        return userProjects;
    }

    // return a project by id and userId
    public Project getProjectById(String projectId, String userId) {
        Project project = new Project();
        List<String> lines = FileDatabaseHelper.readAllLines();

        for (String line : lines) {
            if (line.startsWith("PROJECT:")) {
                Project p = new Project().fromFileString(line);
                if (p != null && p.getProjectId().equals(projectId) && p.getCreatedBy().equals(userId)) {
                    project.setName(p.getName());
                    project.setUpdateProjectId(p.getProjectId());
                    project.setAssignedUser(p.getAssignedUser());
                    project.setCreatedBy(p.getCreatedBy());
                    project.setPlannedhours(p.getPlannedhours());
                }
            }
        }

        return project;
    }
}
