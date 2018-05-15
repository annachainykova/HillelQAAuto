package com.logs;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logs {
    public static void main(String[] args) throws IOException {
            //logsToLogs("LogsToTest");
            fromFileOnlyLogsWithTIme(logsToLogs("LogsToTest"));
        //regex();

    }





































        public static String logsToLogs (String path) throws IOException { File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        List<String> newLines = new ArrayList();

        String currentTime = LocalDateTime.now().toString().replace(':', '-');

        String pathofNew = "newfile" + currentTime + ".txt";
        Path fileToWrite = Paths.get(pathofNew);

        for (File file : listOfFiles) {
            String fileName = file.getPath();

            List<String> lines = Files.readAllLines(Paths.get(fileName));

            for (int i = 0; i < lines.size(); i++) {
                Pattern p = Pattern.compile("(\\s){2}(\\w)*(-)*(\\w)+-(\\d)+");
                Matcher m = p.matcher(lines.get(i));
                if (m.find()) {
                    newLines.add(lines.get(i));
                }
            }

            Files.write(fileToWrite, newLines);

        }
        return pathofNew;
    }

    public static void fromFileOnlyLogsWithTIme(String pathOfNew) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(pathOfNew));
        for (String line : lines) {
            String[] parts = line.split(":  ");
            String part1 = parts[0];
            String part2 = parts[1];

            Pattern p = Pattern.compile("([a-zA-z]*\\s*\\d\\s\\d*:\\d*:\\d*)(\\s*(\\w*-)*\\w*\\s*\\w*)");
            Matcher m = p.matcher(part1);
            m.find();
           String timeOnly = m.group(1);

            System.out.println(timeOnly);
            System.out.println(part2);
        }
    }


    public static void regex() {
        String str = "Feb  4 00:07:18 gw1-ca-ca1 transaction0";
        Pattern p = Pattern.compile("([a-zA-z]*\\s*\\d\\s\\d*:\\d*:\\d*)(\\s*(\\w*-)*\\w*\\s*\\w*)");
        Matcher m = p.matcher("Feb  4 00:07:18 gw1-ca-ca1 transaction0");
        System.out.println(str);
        System.out.println(m.find());
        String re = m.group(1);
        System.out.println(re);

    }




}
