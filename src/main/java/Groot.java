import java.util.Scanner;
import java.util.ArrayList;

public class Groot {
    private static final ArrayList<String> list = new ArrayList<String>();

    public static void iAmGroot() {
        System.out.print("I am Groot. ");
    }

    public static void greet() {
        iAmGroot();
        System.out.println("(Hello. What can I do for you?)");
    }

    public static void echo(String text) {
        iAmGroot();
        System.out.println("(" + text + ")");
    }

    public static void exit() {
        System.out.println("We are Groot. (Goodbye.)");
    }

    public static void add(String text) {
        list.add(text);
        echo("added: " + text);
    }

    public static void displayList() {
        for (int i = 0; i < list.size(); i++) {
            String listItem = Integer.toString(i + 1) + ": " + list.get(i);
            echo(listItem);
        }
    }

    public static void main(String[] args) {
        greet();
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String userInput = input.nextLine();
            if (userInput.equals("bye")) {
                exit = true;
            } else if (userInput.equals("list")) {
                displayList();
            } else {
                add(userInput);
            }
        }
        exit();
    }
}
