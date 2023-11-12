package com.leikooo.yubi.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

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
        chartMapper.createTable("CREATE TABLE chars_" + 123 +
                " (" + "用户" + " VARCHAR(255) NOT NULL, " +
                "销量" + " VARCHAR(255) NOT NULL )");
    }
}