package com.leikooo.yubi.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leikooo.yubi.annotation.AuthCheck;
import com.leikooo.yubi.common.BaseResponse;
import com.leikooo.yubi.common.DeleteRequest;
import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.common.ResultUtils;
import com.leikooo.yubi.constant.ChartConstant;
import com.leikooo.yubi.constant.UserConstant;
import com.leikooo.yubi.exception.BusinessException;
import com.leikooo.yubi.exception.ThrowUtils;
import com.leikooo.yubi.manager.RedisLimiterManager;
import com.leikooo.yubi.model.dto.chart.*;
import com.leikooo.yubi.model.dto.controller.ChartGenController;
import com.leikooo.yubi.model.dto.controller.ChartQueryController;
import com.leikooo.yubi.model.dto.controller.ChartRetryController;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.model.entity.User;
import com.leikooo.yubi.model.vo.BiResponse;
import com.leikooo.yubi.model.vo.ChartVO;
import com.leikooo.yubi.service.ChartService;
import com.leikooo.yubi.service.UserService;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Resource
    private ChartService chartService;

    @Resource
    private UserService userService;

    @Resource
    private RedisLimiterManager redisLimiterManager;

    /**
     * 删除图表
     *
     * @param deleteRequest
     * @param request
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Boolean> deleteChart(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = chartService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     *
     * @param chartUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Boolean> updateChart(@RequestBody final ChartUpdateRequest chartUpdateRequest, HttpServletRequest request) {
        if (chartUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartUpdateRequest, chart);
        boolean result = chartService.updateById(chart);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }


    /**
     * 分页获取图表格封装列表
     *
     * @param chartQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Page<ChartVO>> listChartVOByPage(@RequestBody ChartQueryRequest chartQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(chartQueryRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        ChartQueryController chartQueryController = new ChartQueryController(chartQueryRequest.getChartName(), chartQueryRequest.getGoal(), chartQueryRequest.getChartType(), loginUser.getId(), chartQueryRequest.getCurrent(), chartQueryRequest.getPageSize(), chartQueryRequest.getCreateTime(), chartQueryRequest.getUpdateTime());
        Page<ChartVO> chartVOList = chartService.getChartVOList(chartQueryController);
        return ResultUtils.success(chartVOList);
    }

    @PostMapping("/gen")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<BiResponse> genChart(@RequestPart("file") MultipartFile multipartFile,
                                             final ChartGenRequest chartGenRequest,
                                             HttpServletRequest request) {
        validFile(multipartFile);
        User loginUser = userService.getLoginUser(request);
        // 增加限流器
        redisLimiterManager.doRateLimit("genChartByAi_" + loginUser.getId());
        ChartGenController chartGenController = new ChartGenController(chartGenRequest.getChartName(), chartGenRequest.getGoal(), chartGenRequest.getChartType(), loginUser);
        BiResponse chart = chartService.getChart(multipartFile, chartGenController);
        return ResultUtils.success(chart);
    }

    @PostMapping("/gen/async")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<BiResponse> genChartAsync(@RequestPart("file") MultipartFile multipartFile,
                                                  final ChartGenRequest chartGenRequest,
                                                  HttpServletRequest request) {
        validFile(multipartFile);
        User loginUser = userService.getLoginUser(request);
        // 增加限流器
        redisLimiterManager.doRateLimit("genChartByAiAsync_" + loginUser.getId());
        ChartGenController chartGenController = new ChartGenController(chartGenRequest.getChartName(), chartGenRequest.getGoal(), chartGenRequest.getChartType(), loginUser);
        BiResponse chart = chartService.getChartASYNC(multipartFile, chartGenController);
        return ResultUtils.success(chart);
    }

    @PostMapping("/gen/mq")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<BiResponse> genChartMQ(@RequestPart("file") MultipartFile multipartFile,
                                               final ChartGenRequest chartGenRequest,
                                               HttpServletRequest request) {
        validFile(multipartFile);
        User loginUser = userService.getLoginUser(request);
        // 增加限流器
        redisLimiterManager.doRateLimit("genChartByAiMQ_" + loginUser.getId());
        ChartGenController chartGenController = new ChartGenController(chartGenRequest.getChartName(), chartGenRequest.getGoal(), chartGenRequest.getChartType(), loginUser);
        BiResponse chart = chartService.getChartMQ(multipartFile, chartGenController);
        return ResultUtils.success(chart);
    }


    @PostMapping("/my/list/page")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Page<Chart>> listMyChartByPage(@RequestBody final ChartQueryRequest chartQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(chartQueryRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        ChartQueryController chartQueryController = new ChartQueryController(chartQueryRequest.getChartName(), chartQueryRequest.getGoal(), chartQueryRequest.getChartType(), userId, chartQueryRequest.getCurrent(), chartQueryRequest.getPageSize(), chartQueryRequest.getCreateTime(), chartQueryRequest.getUpdateTime());
        Page<Chart> myChartList = chartService.getMyChartList(chartQueryController);
        return ResultUtils.success(myChartList);
    }

    @PostMapping("/gen/retry")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<BiResponse> retryGenChart(@RequestBody final ChartRetryRequest chartQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(chartQueryRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        ChartRetryController chartRetryController = new ChartRetryController(chartQueryRequest.getId(), loginUser);
        BiResponse chart = chartService.retryGenChart(chartRetryController);
        return ResultUtils.success(chart);
    }

    @GetMapping("/download/csv")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public ResponseEntity<InputStreamResource> downloadCsvData(@RequestBody ChartCsvDataRequest chartCsvDataRequest) throws IOException {
        boolean result = chartService.downloadCsvData(chartCsvDataRequest.getChartId());
        if (!result) {
            throw new BusinessException(ErrorCode.FILE_DOWNLOAD_ERROR);
        }
        // 读取文件
        String filePath = ChartConstant.CHART_CVS_PATH + "charts_" + chartCsvDataRequest.getChartId() + ".csv";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 构建响应实体
        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .contentLength(file.length())
                .body(inputStreamResource);
    }

    /**
     * 校验文件
     *
     * @param multipartFile 文件类型
     */
    private void validFile(MultipartFile multipartFile) {
        // 文件大小
        final long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long TEN_MAX = 1024 * 1024 * 10L;
        if (fileSize > TEN_MAX) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        if (!Arrays.asList("xlsx", "xls").contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
    }
}
