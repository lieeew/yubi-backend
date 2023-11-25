package com.leikooo.yubi.model.dto.controller;

import com.leikooo.yubi.common.PageRequest;
import com.leikooo.yubi.exception.ThrowUtils;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChartQueryController extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图标名称
     */
    private String chartName;

    /**
     * 图表类型
     */
    private String chartType;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * current
     */
    private long current;

    /**
     * pageSize
     */
    private long pageSize;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = -3389509881984782940L;

    public ChartQueryController(String chartName, String goal, String chartType, Long userId, Long current, Long pageSize, Date createTime, Date updateTime) {
        this.goal = goal;
        this.chartName = chartName;
        this.chartType = chartType;
        this.userId = userId;
        this.current = current;
        this.pageSize = pageSize;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}