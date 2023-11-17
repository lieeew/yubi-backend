package com.leikooo.yubi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leikooo.yubi.model.entity.Chart;

import java.util.List;
import java.util.Map;

/**
 * @author liang
 * @description 针对表【chart(图表信息表)】的数据库操作Mapper
 * @createDate 2023-11-06 18:40:34
 * @Entity generator.domain.Chart
 */
public interface ChartMapper extends BaseMapper<Chart> {
    /**
     * 动态的创建数据库
     * @param creatTableSQL
     */
    void createTable(final String creatTableSQL);

    /**
     * 向动态创建的数据库之中插入数据
     *
     * @param insertCVSData
     * @return
     */
    void insertValue(final String insertCVSData);

    /**
     * 查询保存数据表的信息
     *
     * @param tableName
     * @return
     */
    List<Map<String, Object>> queryChartData(final Long tableName);
}




