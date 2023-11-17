package com.leikooo.yubi.service.impl;

import com.leikooo.yubi.common.ErrorCode;
import com.leikooo.yubi.constant.ChartConstant;
import com.leikooo.yubi.exception.ThrowUtils;
import com.leikooo.yubi.manager.AIManager;
import com.leikooo.yubi.model.entity.Chart;
import com.leikooo.yubi.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author leikooo
 * @Description
 */
@SpringBootTest
@Slf4j
class ChartServiceImplTest {
    @Resource
    private AIManager aiManager;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    void asyncProcessChartData() {
        String goal = "分析一下用户趋势";
        String chartType = "折线图";
        String cvsData = "用户,数量\n12,10\n13,12\n14,12";
        CompletableFuture.runAsync(() -> {
            // 发送给 AI 分析数据
            String promote = AIManager.PRECONDITION + "分析需求 " + goal + " \n原始数据如下: " + cvsData + "\n生成图标的类型是: " + chartType;
            System.out.println("111111");
            String resultData = null;
            try {
                resultData = aiManager.sendMesToAIUseXingHuo(promote);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            log.info("response{}", resultData);
        }, threadPoolExecutor);
    }
}