package com.logs;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logs {
    public static void main(String[] args) throws IOException {

        writeMapIntoFile(transactionsOfTime(getOnlyLinesWithTransactions(getListOfFilesInFolder("E:\\Anna\\Logs"))));

    }

    /**
     *
     * @param folderPath path to the folder, which contains only logs files
     * @return list of files in folder as File Array
     * @throws IOException
     */
    public static File[] getListOfFilesInFolder (String folderPath) throws IOException {
        File folder = new File(folderPath);
        return folder.listFiles();
    }


    /**
     *
     * @param listOfFiles Array of Files, that will be read and checked for specific Strings (march pattern)
     * @return List of String, each String must 'match' the specific pattern
     * @throws IOException
     */
    public static List<String> getOnlyLinesWithTransactions (File[] listOfFiles) throws IOException {
        List <String> linesWithTransactionsID = new ArrayList();

        for (File file : listOfFiles) {
            List<String> lines = Files.readAllLines(Paths.get(file.getPath()));

            for (String line : lines) {
                Pattern p = Pattern.compile("(\\s){2}(\\w)*(-)*(\\w)+-(\\d)+");
                Matcher m = p.matcher(line);
                if (m.find()) {
                    linesWithTransactionsID.add(line);
                }
            }
        }
        return linesWithTransactionsID;
    }

    /**
     *
     * @param linesWithTransactionsID
     * Divide lines two DateTime, ServerName and Transactions IDs and save DateTime and Transactions IDs into treeMap
     * @return treeMap which contains time as a key and all transactions for this time as a value
     */
    public static TreeMap<String, String> transactionsOfTime (List<String> linesWithTransactionsID) {

        HashMap <String, String> timeAndTransactions = new HashMap<>();
        for (String line : linesWithTransactionsID) {

            // Spliting each line of list by regex for getting two parts:
            // part1 = DateTime with ServerName;
            // part2 = transactionsIDs only
            String[] parts = line.split(":  ");
            String timeWithServername = parts[0];
            String transactionsIDs = parts[1];

            Pattern p = Pattern.compile("([a-zA-z]*\\s*\\d\\s\\d*:\\d*:\\d*)(\\s*(\\w*-)*\\w*\\s*\\w*)"); //regex to check DateTime and ServerName
            Matcher m = p.matcher(timeWithServername);
            m.find();

            String time = m.group(1); // saving only DateTime
            String serverName = m.group(2);

            //adding values to hashmap with unique key, concat value if needed
            if(timeAndTransactions.containsKey(time)) {
                transactionsIDs = timeAndTransactions.get(time) + "," + transactionsIDs;
            }
            timeAndTransactions.put(time, transactionsIDs);
        }
        return new TreeMap<>(timeAndTransactions);
    }

    /**
     * Write Map into File with specific
     * @param timeAndTransactions
     * @throws IOException
     */
    public static void writeMapIntoFile(TreeMap<String, String> timeAndTransactions) throws FileNotFoundException {
        String currentTime = LocalDateTime.now().toString().replace(':', '-'); //to make specific name of File

        PrintWriter out = new PrintWriter("logsUpdated" + currentTime + ".txt");

        Set<Map.Entry<String, String>> hashSet=timeAndTransactions.entrySet();
        for(Map.Entry<String, String> entry:hashSet ) {

            out.println(entry.getKey() + " :");
            out.println(entry.getValue());
        }
    }






}
