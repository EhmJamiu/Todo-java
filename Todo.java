import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

enum TaskStatus {
    NOT_COMPLETED, COMPLETED

}

class Task {
    int id;
    String title;
    TaskStatus status;

    public void addTask() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the task title:");
        title = scanner.nextLine();
        id = (1111 + (int) (Math.random() * 8888 + 1));
        status = TaskStatus.NOT_COMPLETED;

    }

    public String toString() {
        return "[Task ID: " + id +
                " -- Task Title: " + title +
                " -- Task Status: " + status + "]";
    }

}

public class Todo {
    public static void main(String arg[]) {

        List<Task> storage = new ArrayList<Task>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your favourite To-do App.");
        System.out.println("---->>>> Here, I will help you to manage and keep track your daily activities.");
        System.out.println("Kindly follow the prompt below: ");
        System.out.println("Press 1 - to Add Task");
        System.out.println("Press 2 - to View Tasks");
        System.out.println("Press 3 - to Mark Task as Completed");
        System.out.println("Press 4 - to Delete Task");
        System.out.println("Press 5 - to Exit");

        while (scanner.hasNext()) {
            int todo = 0;

            try {
                todo = scanner.nextInt();
                if (todo == 1) {

                    Task newTask = new Task();
                    newTask.addTask();

                    storage.add(newTask);
                    System.out.println(newTask);
                    System.out.println("New task successfully added...");

                    // Ask if there is next task to perform
                    System.out.println("Want to do more?...");
                    System.out.println("Press 1 - to Add Task");
                    System.out.println("Press 2 - to View Tasks");
                    System.out.println("Press 3 - to Mark Task as Completed");
                    System.out.println("Press 4 - to Delete Task");
                    System.out.println("Press 5 - to Exit");

                } else if (todo == 2) {

                    if (storage.isEmpty()) {
                        System.out.println("No task has been added yet...");
                        System.out.println("You can Press 1 - to add your task");
                    } else {
                        System.out.println("Your task list is shown below.");

                        for (Task task : storage) {
                            System.out.println(task);
                        }

                        System.out.println("Press 1 - to Add Task");
                        System.out.println("Press 3 - to Mark Task as Completed");
                        System.out.println("Press 4 - to Delete Task");
                        System.out.println("Press 5 - to Exit");
                    }

                } else if (todo == 3) {
                    if (storage.isEmpty()) {
                        System.out.println("No task available to mark as completed. ");
                        System.out.println("Press 1 to add task. ");
                    } else {

                        System.out.println("Enter the task ID number: ");
                        int inputId = scanner.nextInt();
                        Iterator<Task> iterator = storage.iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            Task task = iterator.next();
                            if (task.id == inputId) {
                                task.status = TaskStatus.COMPLETED;
                                found = true;
                                System.out.println("Task ID " + task.id + "- marked completed...");
                                System.out.println("Want to do more?...");
                                System.out.println("Press 1 - to Add Task");
                                System.out.println("Press 2 - to View Tasks");
                                System.out.println("Press 3 - to Mark Task as Completed");
                                System.out.println("Press 4 - to Delete Task");
                                System.out.println("Press 5 - to Exit");
                                break;

                            }
                        }

                        if (!found) {
                            System.out.println("The Task ID" + inputId + " you entered does not exist.");
                            System.out.println("Press 1 - to Add Task");
                            System.out.println("Press 2 - to View Tasks");
                            System.out.println("Press 3 - to Mark Task as Completed");
                            System.out.println("Press 4 - to Delete Task");
                            System.out.println("Press 5 - to Exit");
                        }

                    }

                } else if (todo == 4) {
                    if (storage.isEmpty()) {
                        System.out.println("No task available to delete. ");
                        System.out.println("Press 1 to add task. ");
                    } else {
                        System.out.println("Enter the task ID number: ");
                        int inputId = scanner.nextInt();

                        Iterator<Task> iterator = storage.iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            Task task = iterator.next();

                            if (task.id == inputId) {
                                System.out.println(task);
                                iterator.remove();
                                found = true;

                                System.out.println("A task deleted successfully...");
                                System.out.println("Want to do more?...");
                                System.out.println("Press 1 - to Add Task");
                                System.out.println("Press 2 - to View Tasks");
                                System.out.println("Press 3 - to Mark Task as Completed");
                                System.out.println("Press 4 - to Delete Task");
                                System.out.println("Press 5 - to Exit");
                                break; // stop after deleting
                            }
                        }

                        if (!found) {
                            System.out.println("Task with ID " + inputId + " does not exist.");
                            System.out.println("Press 1 - to Add Task");
                            System.out.println("Press 2 - to View Tasks");
                            System.out.println("Press 3 - to Mark Task as Completed");
                            System.out.println("Press 4 - to Delete Task");
                            System.out.println("Press 5 - to Exit");
                        }

                    }

                } else if (todo == 5) {
                    System.out.println("You just exited the app.");
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Wrong input.Try again!");
                scanner.next();

            }
        }

        scanner.close();

    }
}
