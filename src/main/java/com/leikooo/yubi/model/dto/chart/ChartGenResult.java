package com.leikooo.yubi.model.dto.chart;

import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.exception.ThrowUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Getter
@NoArgsConstructor
public class ChartGenResult {
    /**
     * AI 生成的图标数据
     */
    private String genChart;

    /**
     * AI 生成的分析结果
     */
    private String genResult;

    public ChartGenResult(String genChart, String genResult) {
        ThrowUtils.throwIf(StringUtils.isAnyBlank(genChart, genResult), ErrorCode.PARAMS_ERROR);
        this.genChart = genChart;
        this.genResult = genResult;
    }
}
