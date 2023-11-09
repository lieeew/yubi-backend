package com.leikooo.yubi.manager;

import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import com.aliyun.broadscope.bailian.sdk.ApplicationClient;
import com.aliyun.broadscope.bailian.sdk.models.BaiLianConfig;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsRequest;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author leikooo
 * @Description 接入 AI 能力的类
 */
@Component
public class AIManager {

    @Resource
    private AccessTokenClient accessTokenClient;
    /**
     * AI 生成问题的预设条件
     */
    public static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据，用,作为分隔符}\n" +
            "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "【【【【【\n" +
            "{前端 Echarts V5 的 option 配置对象js代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
            "【【【【【\n" +
            "{明确的数据分析结论、越详细越好，不要生成多余的注释}";

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

}
