package com.leikooo.yubi.csv.bean;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
@Setter
public class WriteExampleBean extends CsvBean {

    private String colA;

    private String colB;

    private String colC;

    public WriteExampleBean(String colA, String colB, String colC) {
        this.colA = colA;
        this.colB = colB;
        this.colC = colC;
    }
}