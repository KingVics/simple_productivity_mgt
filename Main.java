import user.*;
import project.Project;
import project.ProjectController;
import task.Task;
import task.TaskController;
import task.Task.TaskStatus;
import java.util.*;

class Main {
    // methods
    private static final UserController userController = new UserController();
    private static final ProjectController projectController = new ProjectController();
    private static final TaskController taskController = new TaskController();
    private static User isLoggedInUser = null;

    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Welcome to your Productivity Suite ===");

        while (running) {
            printActiveMenu();
            System.out.print("Enter option: ");

            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> handleProfileCreation(sc);
                    case 2 -> handleLogin(sc);
                    case 3 -> {
                        running = false;
                        System.out.println("Thank you for using the productivity suite. Goodbye!");
                    }
                    case 4 -> viewProfileDetails();
                    case 5 -> handleProjectMenu(sc);
                    case 6 -> handleTaskMenu(sc);
                    case 7 -> handleLogout();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric choice.");
            }
        }
        sc.close();
    }

    // --- Dynamic Main Menu Views ---
    private static void printActiveMenu() {
        System.out.println("\n---------------------------------");
        if (isLoggedInUser == null) {
            System.out.println("Status: Guest");
            System.out.println("1: Create a Profile");
            System.out.println("2: Login");
            System.out.println("3: Exit Application");
        } else {
            System.out.println(
                    "Status: Logged in as " + isLoggedInUser.getName() + " (ID: " + isLoggedInUser.getId() + ")");
            System.out
                    .println("3: Exit Application\n4: View Profile Details\n5: View Project\n6: View Task\n7: Logout");
        }
    }

    // --- Authentication Operations ---
    private static void handleProfileCreation(Scanner sc) {
        if (isLoggedInUser != null) {
            System.out.println("You are already logged in!");
            return;
        }
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        if (userController.findByEmail(email) != null) {
            System.out.println("A profile with this email already exists! Try logging in.");
            return;
        }

        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        user.setId(); // Auto-generates ID

        User saved = userController.saveUser(user);
        System.out.println("Profile successfully created! Your ID is: " + saved.getId());
    }

    private static void handleLogin(Scanner sc) {
        if (isLoggedInUser != null) {
            System.out.println("You are already logged in.");
            return;
        }
        System.out.print("Enter your registered email to login: ");
        String loginEmail = sc.nextLine();

        User loginUser = userController.findByEmail(loginEmail);
        if (loginUser != null) {
            isLoggedInUser = loginUser;
            System.out.println("Login successful! Welcome back, " + isLoggedInUser.getName() + ".");
        } else {
            System.out.println("No profile found with that email. Please create a profile first.");
        }
    }

    private static void viewProfileDetails() {
        if (isLoggedInUser == null) {
            System.out.println("Access denied. Please login first.");
            return;
        }
        System.out.println("\n--- Profile Details ---");
        System.out.println("ID:    " + isLoggedInUser.getId());
        System.out.println("Name:  " + isLoggedInUser.getName());
        System.out.println("Email: " + isLoggedInUser.getEmail());
        System.out.println("Phone: " + isLoggedInUser.getPhone());
    }

    private static void handleLogout() {
        if (isLoggedInUser == null) {
            System.out.println("You are not currently logged in.");
            return;
        }
        System.out.println("Logging out session for: " + isLoggedInUser.getName());
        isLoggedInUser = null;
    }

    // --- Isolated Project Operations ---
    private static void handleProjectMenu(Scanner sc) {
        if (isLoggedInUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("\n--- Project Management ---");
        System.out.println("1. View All Projects\n2. Create Project");
        System.out.print("Enter Project Option: ");
        int pOpt = Integer.parseInt(sc.nextLine());

        if (pOpt == 1) {
            List<Project> projects = projectController.getAllProjects(isLoggedInUser.getId());
            System.out.println("\n--- Your Projects (" + projects.size() + ") ---");
            for (Project p : projects) {
                System.out.println("ID: " + p.getProjectId() + " | Name: " + p.getName() + " | Planned hours: "
                        + p.getPlannedhours());
            }
        } else if (pOpt == 2) {
            System.out.print("Enter Project Name: ");
            String pName = sc.nextLine();
            System.out.print("Enter Planned Hour: ");
            int plannedHour = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Assigned User ID: ");
            String assigned = sc.nextLine();

            Project newProject = new Project();
            newProject.setProjectId();
            newProject.setName(pName);
            newProject.setPlannedhours(plannedHour);
            newProject.setCreatedBy(isLoggedInUser.getId());
            newProject.setAssignedUser(assigned.trim().isEmpty() ? "null" : assigned);

            projectController.create(newProject);
            System.out.println("Project created successfully!");
        }
    }

    // --- Isolated Task Operations ---
    private static void handleTaskMenu(Scanner sc) {
        if (isLoggedInUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("\n--- Task Management ---");
        System.out.println(
                "1. All Task\n2. Create Task\n3. Assign Task to Project\n4. Update Task Status\n5. Delete Task");
        System.out.print("Enter Task Option: ");
        int taskOption = Integer.parseInt(sc.nextLine());

        switch (taskOption) {
            case 1 -> viewAllTasks();
            case 2 -> createNewTask(sc);
            case 3 -> assignTaskToProject(sc);
            case 4 -> updateTaskStatus(sc);
            case 5 -> deleteTask(sc);
            default -> System.out.println("Invalid Task Option choice.");
        }
    }

    private static void viewAllTasks() {
        List<Task> myTasks = taskController.getAllTask(isLoggedInUser.getId());
        System.out.println("\n--- Your Tasks (" + myTasks.size() + ") ---");
        if (myTasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task t : myTasks) {
                System.out.println("ID: " + t.getTaskId() +
                        " | Name: " + t.getName() +
                        " | Status: " + t.getStatus().getValue() +
                        " | Project: " + t.getProjectId() +
                        " | Duration: " + t.getDuration() + " hours");
            }
        }
    }

    private static void createNewTask(Scanner sc) {
        Task newTask = new Task();
        newTask.setTaskId();

        System.out.print("Enter Task Name: ");
        newTask.setName(sc.nextLine());

        System.out.println("Select Task Status:\n1. TO DO\n2. IN PROGRESS\n3. CANCELED\n4. DONE");
        System.out.print("Enter choice (1-4): ");
        int statusChoice = Integer.parseInt(sc.nextLine());
        newTask.setStatus(switch (statusChoice) {
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.CANCELED;
            case 4 -> TaskStatus.DONE;
            default -> TaskStatus.TODO;
        });

        System.out.print("Enter Duration (in hours): ");
        newTask.setDuration(Integer.parseInt(sc.nextLine()));
        System.out.print("Enter Associated Project ID: ");
        newTask.setProjectId(sc.nextLine());
        newTask.setCreatedBy(isLoggedInUser.getId());

        taskController.create(newTask);
        System.out.println("Task created successfully! ID: " + newTask.getTaskId());
    }

    private static void assignTaskToProject(Scanner sc) {
        System.out.print("Enter Task ID: ");
        String tId = sc.nextLine();
        System.out.print("Enter Project ID: ");
        String pId = sc.nextLine();

        Task updated = taskController.updateTask(tId, pId, isLoggedInUser.getId());
        System.out.println(
                updated != null ? "Task linked to project successfully!" : "Task update failed. Verify ID ownership.");
    }

    private static void updateTaskStatus(Scanner sc) {
        System.out.print("Enter Task ID: ");
        String tId = sc.nextLine();
        System.out.println("Select New Status:\n1. TO DO\n2. IN PROGRESS\n3. CANCELED\n4. DONE");
        System.out.print("Enter choice (1-4): ");
        int choice = Integer.parseInt(sc.nextLine());

        // Map the numeric choice to your TaskStatus Enum
        TaskStatus newStatus = switch (choice) {
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.CANCELED;
            case 4 -> TaskStatus.DONE;
            default -> TaskStatus.TODO;
        };

        // Call the controller to update the text database file safely
        Task updated = taskController.updateTaskStatus(tId, isLoggedInUser.getId(), newStatus);

        if (updated != null) {
            System.out.println("Task status updated successfully to: " + updated.getStatus().getValue());
        } else {
            System.out.println("Update failed. Verify that the Task ID exists and belongs to you.");
        }
    }

    private static void deleteTask(Scanner sc) {
        System.out.print("Enter Task ID to delete: ");
        String tId = sc.nextLine();

        // Call the controller to filter out and remove the task row
        boolean deleted = taskController.deleteTask(tId, isLoggedInUser.getId());

        if (deleted) {
            System.out.println("Task has been successfully deleted.");
        } else {
            System.out.println("Deletion failed. Verify that the Task ID exists and belongs to you.");
        }
    }

}