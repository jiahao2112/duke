package ui;

import manager.TaskManager;

import java.util.Scanner;

public class UserInteraction {
    private static TaskManager taskManager; ;
    private static boolean isExit = false;

    public static void run() {
        greet();
        taskManager = new TaskManager();
        Scanner input = new Scanner(System.in);
        while (!isExit) {
            String userInput = input.nextLine();
            manageUserInput(userInput);
        }
    }

    private static void manageUserInput(String userInput) {
        userInput = userInput.trim(); // remove blank spaces in the front and back from user input
        taskManager.manageTask(userInput);
    }

    public static void fileCorruptedHandler(){
        printMessage("Do you want to continue with an empty list? (y/n)");
        Scanner sc = new Scanner(System.in);
        String userResponse = sc.nextLine();
        if (userResponse.equalsIgnoreCase("y")) {
            TaskManager.clearTasklist();
        }else if (userResponse.equalsIgnoreCase("n")) {
            exit();
        }else{
            printMessage("Invalid input");
            fileCorruptedHandler();
        }
    }

    private static void iAmGroot() {
        System.out.print("I am Groot. ");
    }

    public static void printMessage(String... messages) {
        for (String message : messages) {
            echo(message);
        }
    }

    private static void greet() {
        iAmGroot();
        System.out.println("(Hello. What can I do for you?)");
    }

    public static void echo(String text) { //static so other classes can use for output
        iAmGroot();
        System.out.println("(" + text + ")");
    }

    public static void exit() {
        System.out.println("We are Groot. (Goodbye.)");
        isExit = true;
    }
}
