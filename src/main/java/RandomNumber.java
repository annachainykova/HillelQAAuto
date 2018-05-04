import java.util.Random;
import java.util.Scanner;

public class RandomNumber {
    public static void main(String[] args) {

//        System.out.println(isSameNumber());
//        if(isSameNumber()){
//            System.out.println("You guessed number");
//        } else {
//            enterNumber();
//        }


        Random rand = new Random();
        int x = rand.nextInt(9);
        System.out.println(x);
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number between 0 and 9: ");
        String m = reader.nextLine();
        if(m.matches("\\d") ) {
        int n = Integer.parseInt(m);
                if (n == x) {
                    System.out.println("you win");
                } else if (n < x) {
                    System.out.println("Your number is smaller, try again");
                } else {
                    System.out.println("Your number is bigger, try again");
                }
        } else {
            System.out.println("Please enter valid number");
        }

    }


//    public static int RandomNumber() {
//        Random rand = new Random();
//        int x = rand.nextInt(9);
//        System.out.println(x);
//        return x;
//    }

//    public static int enterNumber() {
//        Scanner reader = new Scanner(System.in);  // Reading from System.in
//        System.out.println("Enter a number: ");
//        int n = reader.nextInt();
//        return n;
//
//    }

//    public static boolean isSameNumber() {
//        return RandomNumber() == enterNumber();
//    }

}
