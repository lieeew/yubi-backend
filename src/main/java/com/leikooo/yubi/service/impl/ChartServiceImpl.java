package com.leikooo.yubi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.exception.ThrowUtils;
import com.leikooo.yubi.manager.AIManager;
import com.leikooo.yubi.mapper.ChartMapper;
import com.leikooo.yubi.model.dto.controller.ChartGenController;
import com.leikooo.yubi.model.dto.controller.ChartQueryController;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.model.vo.ChartVO;
import com.leikooo.yubi.service.ChartService;
import com.leikooo.yubi.utils.ExcelUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @description 针对表【chart(图表信息表)】的数据库操作Service实现
 * @createDate 2023-11-07 09:32:54
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
        implements ChartService {

    @Resource
    private AIManager aiManager;

    @Override
    public List<ChartVO> getChartVO(final List<Chart> charts) {
        if (CollectionUtils.isEmpty(charts)) {
            return new ArrayList<>();
        }
        return charts.stream().map(chart -> new ChartVO(chart.getId(), chart.getGoal(), chart.getChartData(), chart.getChartType(), chart.getGenChart(), chart.getGenResult(), chart.getUserId(), chart.getCreateTime(), chart.getUpdateTime())).collect(Collectors.toList());
    }

    @Override
    public ChartVO getChartVO(final Chart chart) {
        if (chart == null) {
            return null;
        }
        return new ChartVO(chart.getId(), chart.getGoal(), chart.getChartData(), chart.getChartType(), chart.getGenChart(), chart.getGenResult(), chart.getUserId(), chart.getCreateTime(), chart.getUpdateTime());
    }

    @Override
    public Page<ChartVO> getChartVOList(final ChartQueryController chartQueryRequest) {
        if (chartQueryRequest == null) {
            return new Page<>();
        }
        final long current = chartQueryRequest.getCurrent();
        final long size = chartQueryRequest.getPageSize();
        final Long id = chartQueryRequest.getId();
        final String goal = chartQueryRequest.getGoal();
        final Long userId = chartQueryRequest.getUserId();
        final String chartType = chartQueryRequest.getChartType();
        final Date createTime = chartQueryRequest.getCreateTime();
        final Date updateTime = chartQueryRequest.getUpdateTime();
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        queryWrapper.eq(id != null && id > 0, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(goal), "goal", goal);
        queryWrapper.eq(StringUtils.isNotBlank(chartType), "chart_type", chartType);
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        queryWrapper.eq(createTime != null, "creatTime", createTime);
        queryWrapper.eq(updateTime != null, "updateTime", updateTime);
        Page<Chart> chartPage = this.page(new Page<>(current, size), queryWrapper);
        Page<ChartVO> chartVOPage = new Page<>(current, size, chartPage.getTotal());
        List<ChartVO> chartVO = this.getChartVO(chartPage.getRecords());
        chartVOPage.setRecords(chartVO);
        return chartVOPage;
    }

    @Override
    public String getChart(final MultipartFile multipartFile,
                           final ChartGenController chartGenController) {
        ThrowUtils.throwIf(chartGenController == null, ErrorCode.PARAMS_ERROR);
        final String goal = chartGenController.getGoal();
        final String chartType = chartGenController.getChartType();
        // 分析 xlsx 文件
        String cvsData = ExcelUtils.getExcelFileName(multipartFile);
        // 发送给 AI 分析数据
        String promote = AIManager.PRECONDITION + "分析需求 " + goal + " 原始数据如下: " + cvsData + "生成图标的类型是: " + chartType;
        String resultData = aiManager.sendMesToAI(promote);
        String genChart = resultData.split("【【【【【")[1].trim();
        String genResult = resultData.split("【【【【【")[2].trim();
        Chart chart = new Chart(goal, cvsData, chartType, genChart, genResult, chartGenController.getLoginUserId());
        boolean saveResult = this.save(chart);
        Long id = chart.getId();

        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "保存图表信息失败");
        return resultData;
    }

    @Override
    public Page<Chart> getMyChartList(final ChartQueryController chartQueryController) {
        ThrowUtils.throwIf(chartQueryController == null, ErrorCode.PARAMS_ERROR);
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        final String goal = chartQueryController.getGoal();
        queryWrapper.eq(StringUtils.isNotBlank(goal), "goal", goal);
        final String chartType = chartQueryController.getChartType();
        queryWrapper.like(StringUtils.isNotBlank(chartType), "chartType", "%" + chartType + "%");
        final Long userId = chartQueryController.getUserId();
        queryWrapper.eq(userId != null && userId > 0, "userId", userId);
        final Date createTime = chartQueryController.getCreateTime();
        final Date updateTime = chartQueryController.getUpdateTime();
        queryWrapper.le(createTime != null, "creatTime", createTime);
        queryWrapper.le(updateTime != null, "creatTime", createTime);
        Page<Chart> pageData = this.page(new Page<>(chartQueryController.getCurrent(), chartQueryController.getPageSize()), queryWrapper);
        ThrowUtils.throwIf(pageData == null, ErrorCode.SYSTEM_ERROR);
        return pageData;
    }
}




