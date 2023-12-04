package com.leikooo.yubi.csv;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenCSVDatabaseEx {

    public static void main(String[] args) {

        String url = "jdbc:mysql://1.117.228.86:3306/yubi";
        String user = "root";
        String password = "hsphsp";

        String fileName = "src/main/resources/chartCSV/cars.csv";
        Path myPath = Paths.get(fileName);

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement("SELECT * FROM charts_1729689729275838466");
             ResultSet rs = pst.executeQuery()) {

            try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(myPath,
                    StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                writer.writeAll(rs, true);
            }
            (new File(fileName)).delete();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(OpenCSVDatabaseEx.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}