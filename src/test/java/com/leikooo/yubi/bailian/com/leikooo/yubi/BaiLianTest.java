package com.leikooo.yubi.bailian.com.leikooo.yubi;

import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import com.aliyun.broadscope.bailian.sdk.ApplicationClient;
import com.aliyun.broadscope.bailian.sdk.models.BaiLianConfig;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsRequest;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

/**
 * @author leikooo
 * @Description
 */
@SpringBootTest
public class BaiLianTest {

    @Resource
    private AccessTokenClient accessTokenClient;

    @Test
     void test() {
        String token = accessTokenClient.getToken();
        BaiLianConfig config = new BaiLianConfig()
                .setApiKey(token);

        String appId = "95c4f1b6ddee4513b690b64552c22e8d";
        String prompt = "我该如何学习 Java ？";
        CompletionsRequest request = new CompletionsRequest()
                .setAppId(appId)
                .setPrompt(prompt);

        ApplicationClient client = new ApplicationClient(config);
        CompletionsResponse response = client.completions(request);
        System.out.println(response);
    }

}
