package com.leikooo.yubi.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public class CsvReader {

    public List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    public List<String[]> readLineByLine(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }
        }
        return list;
    }

    public List<String[]> readAllLinesUseParser(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVParser parser = new CSVParserBuilder()
                    // 用什么分隔
                    .withSeparator(',')
                    // 是否忽略引号
                    .withIgnoreQuotations(true)
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();
            return csvReader.readAll();
        }
    }


    @Test
    void test() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("charts.csv").toURI());
//        readAllLines(path);
//        readLineByLine(path);
        readAllLinesUseParser(path);
    }
}
