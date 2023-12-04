package com.leikooo.yubi.csv.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SimplePositionBean  {
    @CsvBindByPosition(position = 0)
    private String exampleColOne;

    @CsvBindByPosition(position = 1)
    private String exampleColTwo;
}