package task;

import java.util.ArrayList;
import java.util.List;

import common.FileDatabaseHelper;
import common.createLogic;
import task.Task.TaskStatus;

public class TaskController implements createLogic<Task> {

    @Override
    public Task create(Task task) {
        FileDatabaseHelper.appendLine(task.toFileString());
        return task;
    }

    // filter task by createdby
    public List<Task> getAllTask(String userId) {
        List<Task> userTasks = new ArrayList<>();
        List<String> lines = FileDatabaseHelper.readAllLines();

        for (String line : lines) {
            if (line.startsWith("TASK:")) {
                Task t = new Task().fromFileString(line);
                if (t != null && t.getCreatedBy().equals(userId))
                    ;
                userTasks.add(t);
            }

        }

        return userTasks;
    }

    public Task updateTask(String taskId, String projectId, String userId) {
        List<String> lines = FileDatabaseHelper.readAllLines();
        List<String> fileLines = new ArrayList<>();
        Task updatedTask = null;
        boolean targetFound = false;

        for (String line : lines) {
            if (line.startsWith("TASK:")) {
                Task t = new Task().fromFileString(line);

                // Check if this task belongs to the user and matches the ID we want to change
                if (t != null && t.getTaskId().equals(taskId) && t.getCreatedBy().equals(userId)) {
                    t.setProjectId(projectId);
                    updatedTask = t;
                    fileLines.add(t.toFileString());
                    targetFound = true;
                    continue;
                }
            }
            fileLines.add(line);
        }

        // STEP 2: If we found and updated the item, rewrite the entire file cleanly
        if (targetFound) {
            FileDatabaseHelper.writeAllLines(fileLines);
        }

        return updatedTask;
    }

    public Task updateTaskStatus(String taskId, String userId, TaskStatus statusChoice) {
        List<String> lines = FileDatabaseHelper.readAllLines();
        List<String> fileLines = new ArrayList<>();
        Task updatedTask = null;
        boolean targetFound = false;

        for (String line : lines) {
            if (line.startsWith("TASK:")) {
                Task t = new Task().fromFileString(line);
                if (t != null && t.getTaskId().equals(taskId) && t.getCreatedBy().equals(userId)) {
                    t.setStatus(statusChoice);
                    updatedTask = t;
                    fileLines.add(t.toFileString());
                    targetFound = true;
                    continue;
                }
            }
            fileLines.add(line);
        }

        // STEP 2: If we found and updated the item, rewrite the entire file cleanly
        if (targetFound) {
            FileDatabaseHelper.writeAllLines(fileLines);
        }
        return updatedTask;
    }

    public Boolean deleteTask(String tId, String userId) {
        List<String> lines = FileDatabaseHelper.readAllLines();
        List<String> fileLines = new ArrayList<>();
        boolean itemDeleted = false;

        for (String line : lines) {
            if (line.startsWith("TASK:")) {
                Task t = new Task().fromFileString(line);

                // Verify the task matches the target ID and belongs to the user
                if (t != null && t.getTaskId().equals(tId) && t.getCreatedBy().equals(userId)) {
                    itemDeleted = true;
                    continue;
                }
            }
            fileLines.add(line);
        }

        // STEP 2: Rewrite the file if an item was dropped
        if (itemDeleted) {
            FileDatabaseHelper.writeAllLines(fileLines);
        }

        return itemDeleted;
    }
}
