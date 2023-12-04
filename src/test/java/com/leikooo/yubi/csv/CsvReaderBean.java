package com.leikooo.yubi.csv;


import com.leikooo.yubi.csv.bean.CsvBean;
import com.leikooo.yubi.csv.bean.CsvTransfer;
import com.leikooo.yubi.csv.bean.NamedColumnBean;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Test;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public class CsvReaderBean {

    public List<CsvBean> beanBuilderExample(Path path, Class clazz) throws Exception {
        CsvTransfer csvTransfer = new CsvTransfer();

        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<CsvBean> cb = new CsvToBeanBuilder<CsvBean>(reader)
                    .withType(clazz)
                    .build();
            csvTransfer.setCsvList(cb.parse());
        }
        return csvTransfer.getCsvList();
    }

    @Test
    void test() throws Exception {

        Path path = Paths.get(
                ClassLoader.getSystemResource("charts.csv").toURI());
        List<CsvBean> csvBeans = beanBuilderExample(path, NamedColumnBean.class);
    }
}
