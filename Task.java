import java.util.Scanner;

/**
 * @author Yusuf Olosan
 * @role software engineer
 * @createdOn 31 Tue Mar, 2026
 */

public class Task {
    int id;
    String title;
    TaskStatus status;

    public void addTask(String newTask) {
        title = newTask;
        id = (1111 + (int) (Math.random() * 8888 + 1));
        status = TaskStatus.NOT_COMPLETED;

    }
    @Override
    public String toString() {
        return "Task ID: " + id + " - Title: " + title + " - Status: " + status;
    }



}
