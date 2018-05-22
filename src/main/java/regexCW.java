import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexCW {
    public static void main(String[] args) throws IOException {
        String path = "Logs.txt";
        ArrayList<String> neededInfo = new ArrayList<>(); //what info from activity we are looking for
        neededInfo.add("Login Username:");
        neededInfo.add("Data Object");

        cleanLogs(fileToString(path), neededInfo );
    }



    public static ArrayList<String> fileToString(String path) throws IOException {
        return new ArrayList(Files.readAllLines(Paths.get(path)));
    }


    public static void cleanLogs(ArrayList<String> logs, ArrayList<String> neededInfo) {
        for (String log : logs) {
           Pattern pattern = Pattern.compile("([a-zA-z]{3}\\s{2}\\d{1,2} \\d{2}:\\d{2}:\\d)(?:.*)( Activity: (.)*)]");
           Matcher m = pattern.matcher(log);
           if(m.find()) {
               String news = m.group(2);
               ArrayList<String> allNeededInfo = new ArrayList<>();

               for (String info : neededInfo) {

                   Pattern newPattern = Pattern.compile("(\\[" + info + ".*?\\])");
                   Matcher newMatched = newPattern.matcher(news);
                   if (newMatched.find()) {
                       allNeededInfo.add(newMatched.group(1));
                   }
               }
               String infoArrayIntoString = String.join(", ", allNeededInfo);
               System.out.println(m.group(1) + " Activity: " + infoArrayIntoString);
           }

       }


    }
}
