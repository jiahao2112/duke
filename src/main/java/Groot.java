public class Groot {
    public static void iAmGroot(){
        System.out.print("I am Groot. ");
    }
    public static void greet(){
        iAmGroot();
        System.out.println("(Hello. What can I do for you?)");
        System.out.println();
    }
    public static void exit(){
        System.out.println("We are Groot. (Goodbye.)");
    }
    public static void main(String[] args) {
        greet();
        exit();
    }
}
