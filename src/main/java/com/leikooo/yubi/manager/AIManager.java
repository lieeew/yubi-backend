package com.leikooo.yubi.manager;

import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import com.aliyun.broadscope.bailian.sdk.ApplicationClient;
import com.aliyun.broadscope.bailian.sdk.models.BaiLianConfig;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsRequest;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsResponse;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leikooo
 * @Description 接入 AI 能力的类
 */
@Component
@Slf4j
public class AIManager {
    @Resource
    private AccessTokenClient accessTokenClient;

    @Resource
    private SparkClient sparkClient;

    /**
     * AI 生成问题的预设条件
     */
    public static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据，用,作为分隔符}\n" +
            "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）同时不要使用这个符号 '】'\n" +
            "'【【【【【'\n" +
            "{前端 Echarts V5 的 option 配置对象 JSON 代码, 不要生成任何多余的内容，比如注释和代码块标记}\n" +
            "'【【【【【'\n" +
            "{明确的数据分析结论、越详细越好，不要生成多余的注释} \n"
            + "下面是一个具体的例子的模板："
            + "'【【【【【'\n"
            + "{\"xxx\": }"
            + "'【【【【【'\n" +
            "结论：";

    /**
     * 向 AI 发送请求
     *
     * @return
     */
    public String sendMesToAI(final String content) {
        String token = accessTokenClient.getToken();
        BaiLianConfig config = new BaiLianConfig()
                .setApiKey(token);
        String appId = "2e9ff82a22d445bfb78b73effe8fa4cf";
        CompletionsRequest request = new CompletionsRequest()
                .setAppId(appId)
                .setPrompt(content);

        ApplicationClient client = new ApplicationClient(config);
        CompletionsResponse response = client.completions(request);
        return response.getData().getText();
    }

    /**
     * 向 AI 发送请求
     *
     * @return
     */
    public String sendMesToAIUseXingHuo(final String content) {
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火 AI 返回的结果 {}", responseContent);
        return responseContent;
    }

}
