
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailDomain {
    public static void main(String[] args){
        String toCheck = "email1@gmai89l.com,email2@gmail.com,email3@gmail.com";
        String domain = "@gmail.com";
        if(isValidEmail(divideString(toCheck))) {
            if(isEveryEmailBelongsToDomain(divideString(toCheck), domain)) {
                System.out.println("All emails belongs to proper domains");
            } else {
                System.out.println("There are emails on other domain");
            }
        } else {
            System.out.println("At least one of email is not valid. Please check entered data. ");
        }

    }


    public static HashSet<String> divideString(String test) {
        return new HashSet(Arrays.asList(test.split(",")));
    }

    public static boolean isEveryEmailBelongsToDomain (HashSet<String> set, String domain) {
        int count = 0;
        Pattern p2 = Pattern.compile(domain);
        for (String text : set) {
            Matcher matcher = p2.matcher(text);
            if (matcher.find()) {
                count++;
            }
        }
        if (count == set.size()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(HashSet<String> set) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        
        Pattern ptr = Pattern.compile(emailRegex);
        for (String email : set) {
            Matcher matcher = ptr.matcher(email);
            if(!matcher.find()){
                return false;
            }
        }
        return true;
    }

}
