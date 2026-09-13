package task;

import java.util.UUID;

// status is an enum of (TO DO, 1) (IN PROGRESS, 2), (CANCELED, 3), (DONE, 4)
public class Task {
   public enum TaskStatus {
      TODO(1, "TO DO"),
      IN_PROGRESS(2, "IN PROGRESS"),
      CANCELED(3, "CANCELED"),
      DONE(4, "DONE");

      private final int code;
      private final int id;
      private final String value;

      TaskStatus(int id, String value) {
         this.id = id;
         this.code = id;
         this.value = value;
      }

      public int getId() {
         return id;
      }

      public String getValue() {
         return value;
      }
   }

   private String name;
   private TaskStatus status;
   private String createdBy;
   private String taskId;
   private String projectId;
   private int duration;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public TaskStatus getStatus() {
      return status;
   }

   public void setStatus(TaskStatus status) {
      this.status = status;
   }

   public String getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public String getTaskId() {
      return taskId;
   }

   public void setTaskId() {
      this.taskId = UUID.randomUUID().toString();
   }

   public void setUpdateTaskId(String taskId) {
      this.taskId = taskId;
   }

   public String getProjectId() {
      return projectId;
   }

   public void setProjectId(String projectId) {
      this.projectId = projectId;
   }

   public int getDuration() {
      return duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }

   // helper to save task to database.txt
   public String toFileString() {
      return "TASK:" + taskId + "," +
            name + "," +
            status.name() + "," +
            projectId + "," +
            duration + "," +
            createdBy;
   }

   // helper to deserialization task from database.txt
   public Task fromFileString(String line) {
      if (!line.startsWith("TASK:"))
         return null;
      String content = line.substring(5);
      String[] parts = content.split(",");
      if (parts.length < 6)
         return null;

      Task t = new Task();
      t.setUpdateTaskId(parts[0]);
      t.setName(parts[1]);
      t.setStatus(TaskStatus.valueOf(parts[2]));
      t.setProjectId(parts[3]);
      t.setDuration(Integer.parseInt(parts[4]));
      t.setCreatedBy(parts[5]);
      return t;
   }

   @Override
   public String toString() {
      return "Task [name=" + name + ", status=" + status + ", createdBy=" + createdBy + ", taskId=" + taskId
            + ", projectId=" + projectId + ", duration=" + duration + "]";
   }

}
