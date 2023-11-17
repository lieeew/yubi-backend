package com.leikooo.yubi.job.cycle;

import cn.hutool.core.util.ObjectUtil;
import com.leikooo.yubi.mapper.ChartMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@SpringBootTest
class ReGenChartDataTest {
    @Resource
    private ChartMapper chartMapper;

    @Test
    void doRecommendUser() {
        List<Map<String, Object>> maps = chartMapper.queryChartData(1724396079925678081L);
        List<Set<String>> columnSets = maps.stream()
                .map(Map::keySet)
                .collect(Collectors.toList());
        List<String> columnHeader = columnSets.stream()
                .map(column -> column.stream().filter(ObjectUtil::isNotNull).collect(Collectors.joining(",")))
                .collect(Collectors.toList());
        // 拿到对应的 value 拼接上
        List<String> collect = maps.stream().map(columnData -> {
            StringBuffer sb = new StringBuffer();
            String[] headers = columnHeader.get(0).split(",");
            for (int i = 0; i < headers.length; i++) {
                Integer data = (Integer) columnData.get(headers[i]);
                sb.append(data);
                if (i != headers.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
            return sb.toString();
        }).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(columnHeader);
        // 将 collect 中的数据添加到 stringJoiner
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.add(columnHeader.get(0));
        collect.forEach(stringJoiner::add);
        System.out.println(stringJoiner);
    }
}