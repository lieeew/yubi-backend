package com.leikooo.yubi.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author leikooo
 * @Description
 */
@SpringBootTest
class ChartMapperTest {
    @Resource
    private ChartMapper chartMapper;

    @Test
    void createTable() {
        chartMapper.createTable("CREATE TABLE charts_" + 123 +
                " (" + "用户" + " VARCHAR(255) NOT NULL, " +
                "销量" + " VARCHAR(255) NOT NULL )");
    }

    @Test
    void queryChartData() {
        List<Map<String, Object>> maps = chartMapper.queryChartData(1723990416652247042L);
    }
}