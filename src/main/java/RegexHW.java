import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHW {
    public static void main(String[] args) throws IOException {
        String path = "fileForRegex"; //some path to file
        String split = ",";
        String domain = "@gmail.com";
        if(isValidEmail(divideStringToPartsBy(fileToString(path), split))) {
            if(isEveryEmailBelongsToDomain(divideStringToPartsBy(fileToString(path), split), domain)) {
                System.out.println("All emails belongs to proper domains");
            } else {
                System.out.println("There are emails on other domain");
            }
        } else {
            System.out.println("At least one of email is not valid. Please check entered data. ");
        }

    }

    public static String fileToString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


    public static HashSet<String> divideStringToPartsBy(String test, String regex) {
        return new HashSet(Arrays.asList(test.split(regex)));
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
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //здесь бы мог быть не копипаст, а какой-то красивый regex ;)
        Pattern ptr = Pattern.compile(emailRegex);
        for (String email : set) {
            Matcher matcher = ptr.matcher(email);
            if(!matcher.find()){
                //System.out.println(email);
                return false;
            }
        }
        return true;
    }
}
