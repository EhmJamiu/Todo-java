import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Todo {
    private static final List<Task> storage = new ArrayList<>();

    static {
        try {
            loadFile();
        } catch (URISyntaxException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
//        loadFile();
    }

    public static void main(String arg[]) throws URISyntaxException {
        loadFile();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your favourite To-do App.");
        System.out.println("---->>>> Here, I will help you to manage and keep track your daily activities.");
        System.out.println("Kindly follow the prompt below: ");
        System.out.println("Press 1 - to Add Task");
        System.out.println("Press 2 - to View Tasks");
        System.out.println("Press 3 - Change Task Status");
        System.out.println("Press 4 - to Delete Task");
        System.out.println("Press 5 - to Exit");

        String[] instructions = {"Wanna do more?...", "Press 1 - to Add Task", "Press 2 - to View Tasks", "Press 3 - Change Task Status", "Press 4 - to Delete Task", "Press 5 - to Exit"};
        label:
        while (scanner.hasNext()) {
            String input = scanner.next();
            input = input.trim();
            switch (input) {
                case "1":
                    System.out.println("Enter the task title:");
                    String title = scanner.next();
                    while (title.isBlank()) {
                        System.out.println("Task title cannot be empty. Please enter a valid task title:");
                        title = scanner.next();
                    }
                    Task newTask = new Task();
                    newTask.addTask(title);
//                    .add(task);
                    addTask(newTask);


                    System.out.println(newTask.toString());
                    System.out.println("New task successfully added...");
                    for (String instruction : instructions) {
                        System.out.println(instruction);
                    }
                    break label;
                case "2":
                    if (Todo.storage.size() == 0) {
                        System.out.println("No task has been added yet...");
                        System.out.println("You can Press 1 - to add your task");
                    } else {
                        System.out.println("Your task list is shown below.");

//                        for (Task task : Todo.storage) {
//                            System.out.println(task);
//                        }
//                        getTasks().forEach(System.out::println);
                        getTasks().forEach((task) -> System.out.println(task.toString()));
                        for (int i = 0; i < instructions.length; i++) {
                            if (i == 2) {
                                break;
                            }
                            System.out.println(instructions[i]);
                        }
                    }
                    break;
                case "3":
                    if (Todo.storage.isEmpty()) {
                        System.out.println("No task available to mark as completed. ");
                        System.out.println("Press 1 to add task. ");
                    } else {
                        System.out.println("Enter the task ID number: ");
                        int inputId = scanner.nextInt();
                        Iterator<Task> iterator = Todo.storage.iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            Task task = iterator.next();
                            if (task.id == inputId) {
                                task.status = task.status.equals(TaskStatus.COMPLETED) ? TaskStatus.NOT_COMPLETED : TaskStatus.COMPLETED;
                                found = true;
                                updateTaskStatus(inputId, task.status);
                                System.out.println("Task ID " + task.id + "- marked " + task.status.toString().toLowerCase() + " ...");
                                for (String instruction : instructions) {
                                    System.out.println(instruction);
                                }
                                break;
                            }
                            if (!found) {
                                System.out.println("The Task ID" + inputId + " you entered does not exist.");
                                for (int i = 0; i < instructions.length; i++) {
                                    if (i == 0) {
                                        break;
                                    }
                                    System.out.println(instructions[i]);
                                }
                            }
                        }
                    }
                    break;
                case "4":
                    if (Todo.storage.isEmpty()) {
                        System.out.println("No task available to delete. ");
                        System.out.println("Press 1 to add task. ");
                    } else {
                        System.out.println("Enter the task ID number: ");
                        int inputId = scanner.nextInt();

                        Iterator<Task> iterator = Todo.storage.iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            Task task = iterator.next();

                            if (task.id == inputId) {
                                System.out.println(task);
                                iterator.remove();
                                found = true;
                                deleteTask(inputId);

                                System.out.println("A task deleted successfully...");
                                for (String instruction : instructions) {
                                    System.out.println(instruction);
                                }
                                break; // stop after deleting
                            }
                        }

                        if (!found) {
                            System.out.println("Task with ID " + inputId + " does not exist.");
                            for (int i = 0; i < instructions.length; i++) {
                                if (i == 0) {
                                    break;
                                }
                                System.out.println(instructions[i]);
                            }
                        }

                    }

                    break;
                case "5":
                    System.out.println("You just exited the app.");
                    break label;

                default:
                    System.out.println("Wrong input.Try again!");
                    for (String instruction : instructions) {
                        System.out.println(instruction);
                    }
                    break;
            }
        }
        scanner.close();

    }

    private static void loadFile() throws URISyntaxException {
        // checking if file.txt exists,if not, create new file.txt
        if (InputStream.class.getResourceAsStream("file.txt") == null) {
            System.out.println("file.txt does not exist. Creating file.txt...");
            //System.out.println(Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt"));
            try {
                Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
                Files.createFile(path);
                System.out.println("file.txt created successfully.");
            } catch (Exception e) {
                System.out.println("Error creating file.txt: " + e.getMessage());
            }
        } else {
            System.out.println("file.txt already exists.");
            try {
                Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
                List<String> lines = Files.readAllLines(path);
                //id, name, status
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String title = parts[1].trim();
                        TaskStatus status = TaskStatus.valueOf(parts[1].trim().toUpperCase());
                        Task task = new Task();
                        task.id = id;
                        task.title = title;
                        task.status = status;
                        Todo.storage.add(task);
                    }
                    System.out.println("Task loaded successfully.");
                }
            } catch (Exception e) {
                System.out.println("Error reading file.txt: " + e.getMessage());
            }


        }
    }

    private static void saveToFile() {
        try {
            Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
            List<String> lines = new ArrayList<>();
            for (Task task : Todo.storage) {
                lines.add(task.id + "," + task.title + "," + task.status);
            }
            Files.write(path, lines);
            System.out.println("Tasks saved to file.txt successfully.");
        } catch (Exception e) {
            System.out.println("Error writing to file.txt: " + e.getMessage());
        }
    }

    private static void addTask(Task task) {
        try {
            Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
            String line = task.id + "," + task.title + "," + task.status;
            Files.write(path, Collections.singletonList(line), StandardOpenOption.APPEND);
            System.out.println("Task added successfully.");
        }catch (Exception e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }
    private static void deleteTask(int taskId) {
        try {
            Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    if (id != taskId) {
                        updatedLines.add(line);
                    }

                }
            }
            Files.write(path, updatedLines);
            System.out.println("Task deleted successfully.");

        }catch (Exception e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }

    }
    private static void updateTaskStatus(int taskId, TaskStatus newStatus) {
        try {
            Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    TaskStatus status = TaskStatus.valueOf(parts[2].trim().toUpperCase());
                    if (id == taskId) {
                        status = newStatus;
                    }
                    updatedLines.add(id + "," + title + "," + status);
                }
            }
            Files.write(path, updatedLines);
            System.out.println("Task status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating task status: " + e.getMessage());
        }
    }

    private static List<Task> getTasks() {
        try {
            Path path = Paths.get(Objects.requireNonNull(InputStream.class.getResource("/")).toURI()).resolve("file.txt");
            List<String> lines = Files.readAllLines(path);
            List<Task> tasks = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    TaskStatus status = TaskStatus.valueOf(parts[2].trim().toUpperCase());
                    Task task = new Task();
                    task.id = id;
                    task.title = title;
                    task.status = status;
                    tasks.add(task);
                }
            }
            return tasks;
        }catch (Exception e) {
            System.out.println("Error reading tasks: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
