package Hillel;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class AppTest {
    @DataProvider
    public static Object[][] numbers(){
        return new Object[][] {
                {"5", Boolean.TRUE},
                {"1700", Boolean.TRUE}
        };
    }
    @Test(dataProvider = "numbers")
    public static void isNumberTest1(String number, Boolean expectedResult){
        System.out.println("Input: " + number + " expecting " + expectedResult);
        Assert.assertEquals((Boolean)App.checkNumber(number), expectedResult);
    }

    @Test
    public static void isNumberTest2(){
        Assert.assertTrue(App.checkNumber("Amount: 1600.59"));
    }

    @Test
    public static void isNumberTest14(){
        Assert.assertFalse(App.checkNumber("677..88"));
    }

    @Test
    public static void isNumberTest15(){
        Assert.assertTrue(App.checkNumber(" Amount: 0."));
    }


//    public static void isNumberTest3(){
//        String number = "Amount: 1600" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest4(){
//        String number = "1600.59" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest5(){
//        String number = "0.59" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest6(){
//        String number = "1.59" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest7(){
//        String number = "1599.59" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest8(){
//        String number = "700.59" ;
//        if (checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//    //negative
//    public static void isNumberTest10(){
//        String number = "Amount: 0." ;
//        if (!checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest11(){
//        String number = "Amount: 1601" ;
//        if (!checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest12(){
//        String number = "Amount7: 1500" ;
//        if (!checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isNumberTest13(){
//        String number = "Amount: 677.888" ;
//        if (!checkNumber(number)) {
//            System.out.println("Test passed");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//
//
//
//    public static void isGmailTest1() {
//        String email = "abc@gmail.com";
//        if(isGmail(email)) {
//            System.out.println("Test succeeded");
//        } else {
//            System.out.println("Test failed");
//        }
//    }
//
//    public static void isGmailTest2() {
//        String email = "abc@notgmail.com";
//        if(!isGmail(email)) {
//            System.out.println("Test succeeded");
//        } else {
//            System.out.println("Test failed");
//        }
//    }

}
