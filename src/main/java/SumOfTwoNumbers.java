public class SumOfTwoNumbers {
    public static void main(String[] args) {
        System.out.println(sumOfNumbers(6, 3));
    }

    public static int sumOfNumbers(int num1, int num2) {
        int num3 = -num2;
        return num1-num3;
    }
}
