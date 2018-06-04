import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckNumber {
    public static void main(String[] args) {
        //System.out.println(checkNumber("Amount: 1600.87"));
        isNumberTest1();
        isNumberTest2();
        isNumberTest3();
        isNumberTest4();
        isNumberTest5();
        isNumberTest6();
        isNumberTest7();
        isNumberTest8();
        isNumberTest10();
        isNumberTest11();
        isNumberTest12();
        isNumberTest13();
        isNumberTest14();
        isNumberTest15();

    }
    public static boolean checkNumber(String text) {
        //^(?:Amount: )?? - String must start from 'Amount: ' which must be met one or no one time
        // (?:(?:1600)|(?:1[0-5]\d\d)|(?:\d?\d?\d)) - checks number 0-1600 included
        // (?:1600) - only 1600
        // (?:1[0-5]\d\d) - from 1000 to 1599
        // (?:\d?\d?\d) - from 0 to 999
        // (?:\.\d??\d)??$ String must finishes by point with one or two digits which must be met one or no one time
        String regex = "^(?:Amount: )??(?:(?:1600)|(?:1[0-5]\\d\\d)|(?:\\d?\\d?\\d))(?:\\.\\d??\\d)??$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(matcher.matches()){
            return true;
        } else {
            return false;
        }
    }

    public static void isNumberTest1(){
        String number = "Amount: 1600.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest2(){
        String number = "Amount: 1600.5" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest3(){
        String number = "Amount: 1600" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest4(){
        String number = "1600.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest5(){
        String number = "0.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest6(){
        String number = "1.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest7(){
        String number = "1599.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest8(){
        String number = "700.59" ;
        if (checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }
        //negative
    public static void isNumberTest10(){
        String number = "Amount: 0." ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest11(){
        String number = "Amount: 1601" ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest12(){
        String number = "Amount7: 1500" ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest13(){
        String number = "Amount: 677.888" ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest14(){
        String number = " 677.88" ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    public static void isNumberTest15(){
        String number = " 677..88" ;
        if (!checkNumber(number)) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

}
