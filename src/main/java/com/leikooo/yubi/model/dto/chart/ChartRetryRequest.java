package com.leikooo.yubi.model.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChartRetryRequest implements Serializable {

    private static final long serialVersionUID = -4015423666971233788L;
    /**
     * 图标的 ID
     */
    private Long id;

}