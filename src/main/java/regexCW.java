import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexCW {
    public static void main(String[] args) throws IOException {
        String pathToFile = "Logs.txt";
        ArrayList<String> neededInfo = new ArrayList<>(); //what info from activity we are looking for
        neededInfo.add("Login Username:");
        neededInfo.add("Data Object:");
        neededInfo.add("User Action:");

        cleanLogs(fileToArrayList(pathToFile), neededInfo );
    }


    public static ArrayList<String> fileToArrayList(String path) throws IOException {
        return new ArrayList(Files.readAllLines(Paths.get(path)));
    }


    public static void cleanLogs(ArrayList<String> logs, ArrayList<String> neededInfo) {
        for (String log : logs) {
            // pattern where (^.+? )[a-z]+ saves everything before first letter(which is the start of serverName)
            // everything means Date and full time
            // .* - any info before Activity block
            // (Activity: .*)] saves what activity block contains and check whether there is ] char in the end
           Pattern pattern = Pattern.compile("(^.+? )[a-z]+.*(Activity: .*)]");
           Matcher m = pattern.matcher(log);

           if(m.find()) {
               ArrayList<String> allNeededInfo = new ArrayList<>();

               for (String info : neededInfo) {
                   Pattern showsNull = Pattern.compile("(\\[" + info + " .+?\\])");
                   //  showsNull shows all data even if it is Empty, e.g. [Data Object: ]
                   Pattern notShowsNull = Pattern.compile("(\\[" + info + " [^\\]]+?\\])");
                   // notShowsNull shows data only if there is some info, e.g. [Data Object: Files]
                   Matcher newMatched = notShowsNull.matcher(m.group(2));
                   if (newMatched.find()) {
                       allNeededInfo.add(newMatched.group(1));
                   }
               }
               System.out.println(m.group(1) + " Activity: " + String.join(", ", allNeededInfo));
           }

       }
       
    }
}
