package com.leikooo.yubi.csv.bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;


@Data
public class NamedColumnBean extends CsvBean {

    @CsvBindByName(column = "name")
    private String name;

    // Automatically infer column name as 'Age'
    @CsvBindByName
    private int age;
}