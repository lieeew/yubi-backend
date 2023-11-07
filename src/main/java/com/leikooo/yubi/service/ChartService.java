package com.leikooo.yubi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leikooo.yubi.model.dto.chart.ChartQueryRequest;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.model.vo.ChartVO;

import java.util.List;

/**
 * @author liang
 * @description 针对表【chart(图表信息表)】的数据库操作Service
 * @createDate 2023-11-07 09:32:54
 */
public interface ChartService extends IService<Chart> {
    /**
     * 返回 ChartVOList
     *
     * @param charts
     * @return
     */
    List<ChartVO> getChartVO(final List<Chart> charts);

    /**
     * 返回 ChartVO
     *
     * @param chart
     * @return
     */
    ChartVO getChartVO(Chart chart);

    /**
     * 返回对象列表
     *
     * @param chartQueryRequest
     * @return
     */
    Page<ChartVO> getChartVOList(final ChartQueryRequest chartQueryRequest);


}
