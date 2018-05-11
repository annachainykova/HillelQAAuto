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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logs {
    public static void main(String[] args) throws IOException {
            //logsToLogs("LogsToTest");
        parse("Feb  3 16:18:28 gw2-nc-biva transaction1: Asset id: 5153");

    }

    public static void parse (String log) {

    }






































        public static void logsToLogs (String path) throws IOException { File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        List<String> newLines = new ArrayList();

        String currentTime = LocalDateTime.now().toString().replace(':', '-');

        String pathofNew = "newfile" + currentTime + ".txt";
        Path fileToWrite = Paths.get(pathofNew);

        for (File file : listOfFiles) {
            String fileName = file.getPath();

            List<String> lines = Files.readAllLines(Paths.get(fileName));

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("Asset id:")) {
                    newLines.add(lines.get(i));
                    newLines.add(lines.get(i + 1));
                    newLines.add(lines.get(i + 2));
                }
            }

            Files.write(fileToWrite, newLines);

        }
    }




}
