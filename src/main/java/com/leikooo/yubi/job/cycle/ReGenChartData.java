package com.leikooo.yubi.job.cycle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leikooo.yubi.constant.ChartConstant;
import com.leikooo.yubi.manager.AIManager;
import com.leikooo.yubi.mapper.ChartMapper;
import com.leikooo.yubi.model.dto.chart.ChartGenResult;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.model.enums.ResultEnum;
import com.leikooo.yubi.service.ChartService;
import com.leikooo.yubi.utils.ChartDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @description 向每隔 5分钟 执行一个在数据库中捞取数据的操作
 */
@Component
@Slf4j
public class ReGenChartData {
    @Resource
    private ChartMapper chartMapper;
    @Resource
    private AIManager aiManager;
    @Resource
    private ChartService chartService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Scheduled(cron = "0 0/5 * * * ?") // Every 5 minutes
    public void doUpdateFailedChart() {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", ResultEnum.FAILED.getDes());
        List<Chart> failedCharts = chartMapper.selectList(queryWrapper);
        failedCharts.forEach(this::updateFailedChartAsync);
    }

    /**
     * 同步更新失败的图表
     *
     * @param chart
     */
    private void updateFailedChart(final Chart chart) {
        Long chartId = chart.getId();
        List<Map<String, Object>> chartOriginalData = chartMapper.queryChartData(chartId);
        String cvsData = ChartDataUtil.changeDataToCSV(chartOriginalData);
        ChartGenResult result = ChartDataUtil.getGenResult(aiManager, chart.getGoal(), cvsData, chart.getChartType());
        try {
            chartService.updateById(new Chart(chartId, result.getGenChart(), result.getGenResult(), ResultEnum.SUCCEED.getDes(), ""));
        } catch (Exception e) {
            chartService.updateById(new Chart(chartId, ResultEnum.FAILED.getDes(), e.getMessage()));
            log.error("更新图表数据失败，chartId:{}, error:{}", chartId, e.getMessage());
        }
    }

    /**
     * 异步更新失败的图表
     *
     * @param chart
     */
    private void updateFailedChartAsync(final Chart chart) {
        CompletableFuture.runAsync(() -> {
            updateFailedChart(chart);
        }, threadPoolExecutor);
    }
}