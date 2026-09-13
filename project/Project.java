package project;

import java.util.UUID;

// Project class with getter and setters
public class Project {
    private String name;
    private String assignedUser;
    private int plannedhours;
    private String projectId;
    private String createdBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getPlannedhours() {
        return plannedhours;
    }

    public void setPlannedhours(int plannedhours) {
        this.plannedhours = plannedhours;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId() {
        this.projectId = UUID.randomUUID().toString();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdateProjectId(String createdBy) {
        this.createdBy = createdBy;
    }

    // helper to save project to database.txt
    public String toFileString() {
        return "PROJECT:" + this.projectId + "," +
                this.name + "," +
                this.plannedhours + "," +
                this.createdBy + "," +
                this.assignedUser;
    }

    // helper to deserialization project from database.txt
    public Project fromFileString(String line) {
        if (!line.startsWith("PROJECT:"))
            return null;
        String content = line.substring(8);
        String[] parts = content.split(",");
        if (parts.length < 5)
            return null;

        Project p = new Project();
        p.projectId = parts[0];
        p.name = parts[1];
        p.plannedhours = Integer.parseInt(parts[2]);
        p.createdBy = parts[3];
        p.assignedUser = parts[4];
        return p;
    }

}
