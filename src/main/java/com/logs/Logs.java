package com.logs;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logs {
    public static void main(String[] args) throws IOException {

        writeMapIntoFile(fromFileOnlyLogsWithTIme(getOnlyLinesWithTransactions(getListOfFilesInFolder("E:\\Anna\\Logs"))));

    }


        public static File[] getListOfFilesInFolder (String folderPath) throws IOException {
            File folder = new File(folderPath);
            return folder.listFiles();
        }



        public static List<String> getOnlyLinesWithTransactions (File[] listOfFiles) throws IOException {

        List <String> linesWithTransactionsID = new ArrayList();

        for (File file : listOfFiles) {
            String fileName = file.getPath();

            List<String> lines = Files.readAllLines(Paths.get(fileName));

            for (int i = 0; i < lines.size(); i++) {
                Pattern p = Pattern.compile("(\\s){2}(\\w)*(-)*(\\w)+-(\\d)+");
                Matcher m = p.matcher(lines.get(i));
                if (m.find()) {
                    linesWithTransactionsID.add(lines.get(i));
                }
            }

        }
        return linesWithTransactionsID;
    }

    public static TreeMap<String, String> fromFileOnlyLogsWithTIme(List<String> linesWithTransactionsID) throws IOException {

        HashMap <String, String> timeAndTransactions = new HashMap<>();
        for (String line : linesWithTransactionsID) {
            String[] parts = line.split(":  ");
            String part1 = parts[0];
            String part2 = parts[1];

            Pattern p = Pattern.compile("([a-zA-z]*\\s*\\d\\s\\d*:\\d*:\\d*)(\\s*(\\w*-)*\\w*\\s*\\w*)");
            Matcher m = p.matcher(part1);
            m.find();
           String timeOnly = m.group(1);
           if(timeAndTransactions.containsKey(timeOnly)) {
                part2 = timeAndTransactions.get(timeOnly) + "," + part2;
            }
            timeAndTransactions.put(timeOnly, part2);
        }
        return new TreeMap<>(timeAndTransactions);



    }


    public static void writeMapIntoFile(TreeMap<String, String> treeMap) throws IOException {
        String currentTime = LocalDateTime.now().toString().replace(':', '-');

        PrintWriter out = new PrintWriter("logsUpdated" + currentTime + ".txt");

        Set<Map.Entry<String, String>> hashSet=treeMap.entrySet();
        for(Map.Entry<String, String> entry:hashSet ) {

            out.println(entry.getKey() + " :");
            out.println(entry.getValue());
        }
    }

}
