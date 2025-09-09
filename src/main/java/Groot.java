import java.util.Scanner;

public class Groot {
    TaskManager taskManager = new TaskManager();
    private boolean isExit = false;

    public Groot() {
        greet();
    }

    public static void iAmGroot() {
        System.out.print("I am Groot. ");
    }

    public void greet() {
        iAmGroot();
        System.out.println("(Hello. What can I do for you?)");
    }

    public static void echo(String text) { //static so other classes can use for output
        iAmGroot();
        System.out.println("(" + text + ")");
    }

    public void exit() {
        System.out.println("We are Groot. (Goodbye.)");
    }

    public void manageUserInput(String userInput) {
        userInput = userInput.trim(); // remove blank spaces in the front and back from user input
        if (userInput.equals("bye")) {
            isExit = true;
        } else {
            taskManager.manageTask(userInput);
        }
    }

    public static void main(String[] args) {
        Groot groot = new Groot();
        Scanner input = new Scanner(System.in);
        while (!groot.isExit) {
            String userInput = input.nextLine();
            groot.manageUserInput(userInput);
        }
        groot.exit();
    }
}
