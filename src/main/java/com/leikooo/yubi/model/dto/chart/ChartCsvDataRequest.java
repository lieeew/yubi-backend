package com.leikooo.yubi.model.dto.chart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Getter
@NoArgsConstructor
@Setter
public class ChartCsvDataRequest {
    /**
     * 图标的 ID
     */
    private String chartId;

    public ChartCsvDataRequest(String chartId) {
        this.chartId = chartId;
    }
}
