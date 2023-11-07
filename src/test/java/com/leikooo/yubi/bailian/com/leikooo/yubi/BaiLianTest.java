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
        String accessKeyId = "**";
        String accessKeySecret = "**";
        String agentKey = "**";

        AccessTokenClient accessTokenClient = new AccessTokenClient(accessKeyId, accessKeySecret, agentKey);
        String token = accessTokenClient.getToken();

        BaiLianConfig config = new BaiLianConfig()
                .setApiKey(token);

        String appId = "7f5e9ba897b34f4b94e2e5f68e7322aa";
        String prompt = "帮我查询下酒店";
        CompletionsRequest request = new CompletionsRequest()
                .setAppId(appId)
                .setPrompt(prompt);

        ApplicationClient client = new ApplicationClient(config);
        CompletionsResponse response = client.completions(request);
        System.out.println(response);
    }

}
