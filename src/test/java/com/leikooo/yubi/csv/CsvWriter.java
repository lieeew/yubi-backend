package com.leikooo.yubi.csv;

import com.opencsv.CSVWriter;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter {
    public String writeLineByLine(List<String[]> lines, Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            for (String[] line : lines) {
                writer.writeNext(line);
            }
            return IOUtils.toString(path.toUri());
        }
    }

    public String writeAllLines(List<String[]> lines, Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
        return Helpers.readFile(path);
    }

    @Test
    void test() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("charts.csv").toURI());
        writeLineByLine(Helpers.fourColumnCsvString(), path);
        writeAllLines(Helpers.twoColumnCsvString(), path);
    }
}