package com.logs;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;






public class LogsNew {

    public static void main(String[] args) throws IOException, ParseException {
        writeMapIntoFile(
                howManyTimesPerPeriod(
                        uniteTransactionsOfSpecificPeriod(
                                combineTransactionsOfDay(
                                        getOnlyLinesWithTransactions(
                                                getAllLinesFromFiles(
                                                        getListOfFilesInFolder("E:\\Anna\\Logs")))), 14)));


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
     * @param listOfFiles Array of Files
     * @return ArrayList which consists of all lines from each file
     * @throws IOException
     */
    public static List <String> getAllLinesFromFiles (File[] listOfFiles) throws IOException {
        List <String> linesOfFiles = new ArrayList();

        for (File file : listOfFiles) {
            List<String> linesOfFile = Files.readAllLines(Paths.get(file.getPath()));
            for (String line : linesOfFile) {
                linesOfFiles.add(line);
            }
        }
        return linesOfFiles;
    }


    /**
     *
     * @param allLinesFromFiles ArrayList of all lines
     * @return List of String, each String must 'match' the specific pattern
     * @throws IOException
     */
    public static List <String> getOnlyLinesWithTransactions (List <String> allLinesFromFiles)  {
        List <String> linesWithTransactionsID = new ArrayList();
            for (String line : allLinesFromFiles) {
                //this pattern checks sequence that must match TranscationsID
                Pattern p = Pattern.compile("(\\s){2}(\\w)*(-)*(\\w)+-(\\d)+");
                Matcher m = p.matcher(line);
                if (m.find()) {
                    linesWithTransactionsID.add(line);
                }
            }
        return linesWithTransactionsID;
    }



    /**
     *
     * @param linesWithTransactionsID
     * Divide lines to DateTime, ServerName and Transactions IDs and save DateTime and Transactions IDs into treeMap
     * @return treeMap which contains time as a key and all transactions for this time(each day) as a value
     */
    public static TreeMap <LocalDate, String> combineTransactionsOfDay (List <String> linesWithTransactionsID) throws ParseException {

        TreeMap <LocalDate, String> timeAndTransactions = new TreeMap<>();

        for (String line : linesWithTransactionsID) {

            // Spliting each line of list by regex for getting two parts:
            // part1 = DateTime with ServerName;
            // part2 = transactionsIDs only
            String[] parts = line.split(":  ");
            String timeWithServerName = parts[0];
            String transactionsIDs = parts[1];

            //regex to check DateTime and ServerName
            String regex = "([a-zA-z]{3}\\s{2}\\d{1,2})(\\s{1}\\d{2}:\\d{2}:\\d{2})(\\s*(\\w*-)*\\w*\\s*\\w*)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(timeWithServerName);
            m.find();

            String date = m.group(1); // saving only Date
            String datePlusYear = date + " 2018"; //adding year, must be to work with LocalDate

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM  d yyyy");
            LocalDate dateEvery = LocalDate.parse(datePlusYear, formatter);

            //adding values to treemap with unique key, concat value if needed
            if(timeAndTransactions.containsKey(dateEvery)) {
                transactionsIDs = timeAndTransactions.get(dateEvery) + "," + transactionsIDs;
            }
            timeAndTransactions.put(dateEvery, transactionsIDs);
        }
        return new TreeMap<>(timeAndTransactions);
    }

    /**
     *
     * @param timeAndTransactions - treeMap which contains all logs for every day. Where key is a LocalDate
     *                              and value is a String of all logs per that day
     * @param periodInDays - for how many days one period lasts
     * @return treeMap which consists of name of period as a key(String) and all logs for this period as a value(String)
     */
    public static TreeMap <String, String> uniteTransactionsOfSpecificPeriod(
                                                TreeMap <LocalDate, String> timeAndTransactions, int periodInDays) {
        LocalDate firstDateOfPeriod = timeAndTransactions.firstKey();
        LocalDate lastDateOfPeriod = firstDateOfPeriod.plusDays(periodInDays);

        TreeMap <String, String> transactionsForSpecificPeriod= new TreeMap<>();

        String logsOfPeriod = "";

        LocalDate currentDate = timeAndTransactions.firstKey();

        while(currentDate.isBefore(timeAndTransactions.lastKey().plusDays(1))) {
            while(currentDate.isBefore(lastDateOfPeriod) && currentDate.isBefore(timeAndTransactions.lastKey().plusDays(1))) {
                if(timeAndTransactions.containsKey(currentDate)) {
                    if (logsOfPeriod.length() == 0) {
                        logsOfPeriod = (timeAndTransactions.get(currentDate));
                    } else {
                        logsOfPeriod = logsOfPeriod + "," + timeAndTransactions.get(currentDate);
                    }
                }
                currentDate = currentDate.plusDays(1);
            }
            transactionsForSpecificPeriod.put(firstDateOfPeriod + " - " + lastDateOfPeriod.minusDays(1), logsOfPeriod);
            logsOfPeriod = "";
            firstDateOfPeriod = firstDateOfPeriod.plusDays(periodInDays);
            lastDateOfPeriod = lastDateOfPeriod.plusDays(periodInDays);
        }
        return transactionsForSpecificPeriod;
    }


    /**
     *
     * @param transactionsForSpecificPeriod treeMap which consists of name of period as a key(String)
     *                            and all logs for this period as a value(String)
     * @return treeMap which consists of name of period as a key(String)
     *                            and every unique logs for this period with occurrences per period as a value(String)
     *                            if there is no transactions per period will return text
     *                            "There is no transactions for this period" as a value
     */

    public static TreeMap <String, String> howManyTimesPerPeriod(TreeMap <String, String> transactionsForSpecificPeriod) {
        TreeMap <String, String> transactionsForSpecificPeriodWithOccurrences = new TreeMap<>();
        String valuePlusOccurrence;
        String allValuePlusOccurrencePerPeriod;
        for (String period : transactionsForSpecificPeriod.keySet()) {
            String logsOfPeriod = transactionsForSpecificPeriod.get(period);
            if(logsOfPeriod.length() == 0) {
                logsOfPeriod = "There is no transactions for this period";
                allValuePlusOccurrencePerPeriod = logsOfPeriod;
            } else {
                ArrayList<String> lists = new ArrayList<>(Arrays.asList(logsOfPeriod.split(",")));
                TreeMap<String, Integer> occurrenceOfEach = new TreeMap<>();
                for (String list : lists) {
                    if (occurrenceOfEach.containsKey(list)) {
                        occurrenceOfEach.put(list, occurrenceOfEach.get(list) + 1);
                    } else {
                        occurrenceOfEach.put(list, 1);
                    }
                }
                ArrayList<String> allValuePlusOccurrencePerPeriodArray = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : occurrenceOfEach.entrySet()) {
                    valuePlusOccurrence = entry.getKey() + ": " + entry.getValue();
                    allValuePlusOccurrencePerPeriodArray.add(valuePlusOccurrence);
                }
                //to cast arraylist into string
                allValuePlusOccurrencePerPeriod = String.join(", ", allValuePlusOccurrencePerPeriodArray);
            }
            transactionsForSpecificPeriodWithOccurrences.put(period,  allValuePlusOccurrencePerPeriod);
        }
        return transactionsForSpecificPeriodWithOccurrences;

    }

    /**
     * Write Map into File with specific
     * @param timeAndTransactions
     * @throws IOException
     */
    public static void writeMapIntoFile(TreeMap<String, String> timeAndTransactions) throws FileNotFoundException {
        String currentTime = LocalDateTime.now().toString().replace(':', '-'); //to make specific name of File

        PrintWriter out = new PrintWriter("logsUpdated" + currentTime + ".txt");

        Set <Map.Entry <String, String>> hashSet = timeAndTransactions.entrySet();
        for(Map.Entry <String, String>  entry: hashSet ) {

            out.println(entry.getKey() + ":");
            out.println(entry.getValue());
        }
    }
}
