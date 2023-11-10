package com.leikooo.yubi.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

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
        chartMapper.createTable("chars_12345", "城市", "人口");
    }
}