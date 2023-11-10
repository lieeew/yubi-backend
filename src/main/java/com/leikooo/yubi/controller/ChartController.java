package com.leikooo.yubi.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leikooo.yubi.annotation.AuthCheck;
import com.leikooo.yubi.common.BaseResponse;
import com.leikooo.yubi.common.DeleteRequest;
import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.common.ResultUtils;
import com.leikooo.yubi.constant.UserConstant;
import com.leikooo.yubi.exception.BusinessException;
import com.leikooo.yubi.exception.ThrowUtils;
import com.leikooo.yubi.model.dto.chart.ChartGenRequest;
import com.leikooo.yubi.model.dto.chart.ChartQueryRequest;
import com.leikooo.yubi.model.dto.chart.ChartUpdateRequest;
import com.leikooo.yubi.model.dto.controller.ChartGenController;
import com.leikooo.yubi.model.dto.controller.ChartQueryController;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.model.entity.User;
import com.leikooo.yubi.model.vo.ChartVO;
import com.leikooo.yubi.service.ChartService;
import com.leikooo.yubi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
    /**
     * 创建图表
     *
     * @param chartAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Long> addChart(@RequestBody ChartGenRequest chartAddRequest, HttpServletRequest request) {
        if (chartAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        // todo 这里没有 setter 方法好像不行的
        BeanUtils.copyProperties(chartAddRequest, chart);
        boolean result = chartService.save(chart);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chart.getId());
    }

    /**
     * 删除图表
     *
     * @param deleteRequest
     * @param request
     * @return
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
        ChartQueryController chartQueryController = new ChartQueryController(chartQueryRequest.getGoal(), chartQueryRequest.getChartType(), loginUser.getId(), chartQueryRequest.getCreateTime(), chartQueryRequest.getUpdateTime());
        Page<ChartVO> chartVOList = chartService.getChartVOList(chartQueryController);
        return ResultUtils.success(chartVOList);
    }

    @PostMapping("/gen")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<String> genChart(@RequestPart("file") MultipartFile multipartFile,
                                         final ChartGenRequest chartGenRequest,
                                         HttpServletRequest request) {
        validFile(multipartFile);
        User loginUser = userService.getLoginUser(request);
        ChartGenController chartGenController = new ChartGenController(chartGenRequest.getGoal(), chartGenRequest.getChartType(), loginUser);
        String cvsData = chartService.getChart(multipartFile, chartGenController);
        return ResultUtils.success(cvsData);
    }

    @PostMapping("/my/list/page")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public BaseResponse<Page<Chart>> listMyChartByPage(@RequestBody final ChartQueryRequest chartQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(chartQueryRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        ChartQueryController chartQueryController = new ChartQueryController(chartQueryRequest.getGoal(), chartQueryRequest.getChartType(), userId, chartQueryRequest.getCreateTime(), chartQueryRequest.getUpdateTime());
        Page<Chart> myChartList = chartService.getMyChartList(chartQueryController);
        return ResultUtils.success(myChartList);
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
        final long ONE_MAX = 1024 * 1024 * 10L;
        if (fileSize > ONE_MAX) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        if (!Objects.equals("xlsx", fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
    }
}
