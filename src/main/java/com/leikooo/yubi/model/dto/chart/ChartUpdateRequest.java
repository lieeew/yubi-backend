package com.leikooo.yubi.model.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChartUpdateRequest implements Serializable {

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;


    /**
     * 创建用户 id
     */
    private Long userId;


    private static final long serialVersionUID = 3527462006741457835L;
}