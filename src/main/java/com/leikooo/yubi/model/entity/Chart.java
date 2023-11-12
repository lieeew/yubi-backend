package com.leikooo.yubi.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.exception.ThrowUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.Date;

/**
 * 图表信息表
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @TableName chart
 */
@TableName(value ="chart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Chart(String goal, String chartData, String chartType, String genChart, String genResult, Long userId) {
        ThrowUtils.throwIf(StringUtils.isAnyBlank(goal, chartData, chartType, genChart, genResult) && (userId == null || userId < 0), ErrorCode.PARAMS_ERROR);
        this.goal = goal;
        this.chartData = chartData;
        this.chartType = chartType;
        this.genChart = genChart;
        this.genResult = genResult;
        this.userId = userId;
    }

    public Chart(String goal, String chartType, String genChart, String genResult, Long userId) {
        this.goal = goal;
        this.chartType = chartType;
        this.genChart = genChart;
        this.genResult = genResult;
        this.userId = userId;
    }
}