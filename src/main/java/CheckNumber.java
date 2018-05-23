import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckNumber {
    public static void main(String[] args) {
        System.out.println(checkNumber("Amount: 1600.87"));
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
}
