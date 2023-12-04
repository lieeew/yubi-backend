package com.leikooo.yubi.csv;


import com.leikooo.yubi.csv.bean.CsvBean;
import com.leikooo.yubi.csv.bean.WriteExampleBean;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public class CsvWritingBean {

    public String writeCsvFromBean(Path path) throws Exception {

        List<CsvBean> sampleData = Arrays.asList(
                new WriteExampleBean("Test1", "sfdsf", "fdfd"),
                new WriteExampleBean("Test2", "ipso", "facto")
        );

        try (Writer writer = new FileWriter(path.toString())) {

            StatefulBeanToCsv<CsvBean> sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
                    // 禁用引号
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(sampleData);
        }
        return Helpers.readFile(path);
    }

    @Test
    void test() throws Exception {
        Path path = new File("src/main/resources/chartCSV/c.csv").toPath();
        writeCsvFromBean(path);
    }
}
