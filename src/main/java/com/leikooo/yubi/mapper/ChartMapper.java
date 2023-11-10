package com.leikooo.yubi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leikooo.yubi.model.entity.Chart;

/**
* @author liang
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2023-11-06 18:40:34
* @Entity generator.domain.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    void createTable(String tableName, String dataName, String dataValue);
}




