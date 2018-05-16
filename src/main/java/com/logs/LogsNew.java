package com.logs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;






// To Do
// Fix 2WeeksDate
// Improve all methods
// Add comments
// delete useless methods





public class LogsNew {
    public static void main(String[] args) throws IOException, ParseException {

        //writeMapIntoFile(transactionsOfTime(getOnlyLinesWithTransactions(getListOfFilesInFolder("E:\\Anna\\Logs"))));
        //date();
        //howManyTimesPerDay(transactionsOfTime(getOnlyLinesWithTransactions(getListOfFilesInFolder("E:\\Anna\\Logs"))));
        writeMapIntoFile(howManyTimesPerWeek(uniteTransactionForTwoWeeks(transactionsOfTime(getOnlyLinesWithTransactions(getListOfFilesInFolder("E:\\Anna\\Logs"))))));
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
    public static TreeMap<LocalDate, String> transactionsOfTime (List<String> linesWithTransactionsID) throws ParseException {

        HashMap <LocalDate, String> timeAndTransactions = new HashMap<>();
        for (String line : linesWithTransactionsID) {

            // Spliting each line of list by regex for getting two parts:
            // part1 = DateTime with ServerName;
            // part2 = transactionsIDs only
            String[] parts = line.split(":  ");
            String timeWithServername = parts[0];
            String transactionsIDs = parts[1];

            Pattern p = Pattern.compile("([a-zA-z]{3}\\s{2}\\d{1,2})(\\s{1}\\d{2}:\\d{2}:\\d{2})(\\s*(\\w*-)*\\w*\\s*\\w*)"); //regex to check DateTime and ServerName
            Matcher m = p.matcher(timeWithServername);
            m.find();

            String date = m.group(1); // saving only Date
            String datePlusYear = date + " 2018";
            String dateTime = m.group(1) + " " + m.group(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM  d yyyy");
            LocalDate dateEvery = LocalDate.parse(datePlusYear, formatter);

            String serverName = m.group(3);

            //adding values to hashmap with unique key, concat value if needed
            if(timeAndTransactions.containsKey(dateEvery)) {
                transactionsIDs = timeAndTransactions.get(dateEvery) + "," + transactionsIDs;
            }
            timeAndTransactions.put(dateEvery, transactionsIDs);
        }
        return new TreeMap<>(timeAndTransactions);
    }

    /**
     * Write Map into File with specific
     * @param timeAndTransactions
     * @throws IOException
     */
    public static void writeMapIntoFile(TreeMap <String, TreeMap <String, Integer>> timeAndTransactions) throws FileNotFoundException {
        String currentTime = LocalDateTime.now().toString().replace(':', '-'); //to make specific name of File

        PrintWriter out = new PrintWriter("logsUpdated" + currentTime + ".txt");

        Set<Map.Entry<String, TreeMap<String, Integer>>> hashSet=timeAndTransactions.entrySet();
        for(Map.Entry<String, TreeMap<String, Integer>> entry:hashSet ) {

            out.println(entry.getKey() + " :");
            out.println(entry.getValue());
        }
    }

    public static  TreeMap<String, String> uniteTransactionForTwoWeeks(TreeMap<LocalDate, String> timeAndTransactions) {
        LocalDate firstDayEver = timeAndTransactions.firstKey();
        TreeMap <String, String> weeksAndTransactions= new TreeMap<>();
        String myString = "";
        List<String> allString = new ArrayList<>();
        LocalDate currentDate = firstDayEver;
        while(currentDate.isBefore(timeAndTransactions.lastKey().plusDays(1))) {
            while(currentDate.isBefore(firstDayEver.plusWeeks(2))) {
            if(timeAndTransactions.containsKey(currentDate)) {
                myString = myString + "," + (timeAndTransactions.get(currentDate));

            }
                currentDate = currentDate.plusDays(1);
            }
            weeksAndTransactions.put(firstDayEver + " - " + firstDayEver.plusWeeks(2).minusDays(1), myString);
            allString.add(myString);
            myString = "";
            firstDayEver = firstDayEver.plusWeeks(2);
        }
        return weeksAndTransactions;

    }

    public static TreeMap<String, TreeMap<String, Integer>> howManyTimesPerWeek(TreeMap<String, String> weeksAndTransactions) {
        TreeMap<String, TreeMap<String, Integer>> finish = new TreeMap<>();
        for (String key : weeksAndTransactions.keySet()) {
            String one = weeksAndTransactions.get(key);
            List<String> lists = new ArrayList<>(Arrays.asList(one.split(",")));
            TreeMap<String, Integer> occurenceOfEach = new TreeMap<>();
            for (String list : lists) {
                if (occurenceOfEach.containsKey(list)) {
                    occurenceOfEach.put(list, occurenceOfEach.get(list) + 1);
                } else {
                    occurenceOfEach.put(list, 1);
                }
            }
//            for (Map.Entry<String, Integer> entry : occurenceOfEach.entrySet()) {
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//            }
            finish.put(key, occurenceOfEach);
        }
        return finish;

    }




    public static void howManyTimesInString(String myString) {

        List <String> lists = new ArrayList<>(Arrays.asList(myString.split(",")));
        for(String list : lists) {
            System.out.println(list);
        }
        TreeMap<String, Integer> occurenceOfEach = new TreeMap<>();
        for (String list : lists) {
            if(occurenceOfEach.containsKey(list)){
                occurenceOfEach.put(list, occurenceOfEach.get(list)+1);
            } else {
                occurenceOfEach.put(list, 1);
            }
        }
        for(Map.Entry<String, Integer> entry : occurenceOfEach.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


    public static void howManyTimesPerDay(TreeMap<String, String> timeAndTransactions) {

        String one = timeAndTransactions.get("Feb  1 2018");
        List <String> lists = new ArrayList<>(Arrays.asList(one.split(",")));
        for(String list : lists) {
            System.out.println(list);
        }
        TreeMap<String, Integer> occurenceOfEach = new TreeMap<>();
        for (String list : lists) {
            if(occurenceOfEach.containsKey(list)){
                occurenceOfEach.put(list, occurenceOfEach.get(list)+1);
            } else {
                occurenceOfEach.put(list, 1);
            }
        }
        for(Map.Entry<String, Integer> entry : occurenceOfEach.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }






    public static void date() throws ParseException {
        String dateAndTime = "Feb  4 2018";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM  d yyyy");
        LocalDate date = LocalDate.parse(dateAndTime, formatter);
        LocalDate date2 = date.plusWeeks(2).minusDays(1);
        System.out.println(date);
        System.out.println(date2);

    }



}
