package com.leikooo.yubi.utils;

import cn.hutool.core.util.ObjectUtil;
import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.exception.BusinessException;
import com.leikooo.yubi.exception.ThrowUtils;
import com.leikooo.yubi.manager.AIManager;
import com.leikooo.yubi.model.dto.chart.ChartGenResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description 把查询到的数据转换成字符串
 * <p>
 * 用户id, 用户数据, 用户增量
 * 1, 200, 10
 * 2, 200, 20
 * 3, 800, 10
 */
@Component
@Slf4j
public class ChartDataUtil {

    public static String changeDataToCSV(List<Map<String, Object>> chartOriginalData) {
        List<Set<String>> columnSets = chartOriginalData.stream()
                .map(Map::keySet)
                .collect(Collectors.toList());
        List<String> columnHeader = columnSets.stream()
                .map(column -> column.stream().filter(ObjectUtil::isNotNull).collect(Collectors.joining(",")))
                .collect(Collectors.toList());
        // 拿到对应的 value 拼接上
        List<String> columnDataList = chartOriginalData.stream().map(columnData -> {
            StringBuilder result = new StringBuilder();
            String[] headers = columnHeader.get(0).split(",");
            for (int i = 0; i < headers.length; i++) {
                String data = (String) columnData.get(headers[i]);
                result.append(data);
                if (i != headers.length - 1) {
                    result.append(",");
                }
            }
            result.append("\n");
            return result.toString();
        }).collect(Collectors.toList());
        // 将 columnDataList 中的数据添加到 stringJoiner
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.add(columnHeader.get(0));
        columnDataList.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * 获取 AI 生成结果
     * @param aiManager  AI 能力
     * @param goal
     * @param cvsData
     * @param chartType
     * @return
     */
    public static ChartGenResult getGenResult(final AIManager aiManager, final String goal, final String cvsData, final String chartType) {
        String promote = AIManager.PRECONDITION + "分析需求 " + goal + " \n原始数据如下: " + cvsData + "\n生成图标的类型是: " + chartType;
        String resultData = aiManager.sendMesToAIUseXingHuo(promote);
        log.info("AI 生成的信息: {}", resultData);
        ThrowUtils.throwIf(resultData.split("【【【【【").length < 3, ErrorCode.SYSTEM_ERROR);
        String genChart = resultData.split("【【【【【")[1].trim();
        String genResult = resultData.split("【【【【【")[2].trim();
        return new ChartGenResult(genChart, genResult);
    }
}
