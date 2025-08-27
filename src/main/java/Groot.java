import java.util.Scanner;

public class Groot {
    public static void iAmGroot(){
        System.out.print("I am Groot. ");
    }
    public static void greet(){
        iAmGroot();
        System.out.println("(Hello. What can I do for you?)");
    }
    public static void echo(String command){
        iAmGroot();
        System.out.println("("+command+")");

    }
    public static void exit(){
        System.out.println("We are Groot. (Goodbye.)");
    }
    public static void main(String[] args) {
        greet();
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            String command = input.nextLine();
            if (command.equals("bye")){
                exit = true;
            }
            else{
                echo(command);
            }
        }
        exit();
    }
}
