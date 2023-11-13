package com.leikooo.yubi.manager;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leikooo
 * @Description 讯飞星火官方文档
 */
@Slf4j
public class AIManagerTest {
    public static final String appid = "";
    public static final String apiSecret = "";
    public static final String apiKey = "";

    @Test
    void test() {
        SparkClient sparkClient = new SparkClient();
        // 设置认证信息
        sparkClient.appid = appid;
        sparkClient.apiKey = apiKey;
        sparkClient.apiSecret = apiSecret;

        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.userContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                "分析需求：\n" +
                "{数据分析的需求或者目标}\n" +
                "原始数据：\n" +
                "{csv格式的原始数据，用,作为分隔符}\n" +
                "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                "【【【【【\n" +
                "{前端 Echarts V5 的 option 配置对象 JSON 代码, 不要生成任何多余的内容，比如注释和代码块标记}\n" +
                "【【【【【 \n" +
                "{明确的数据分析结论、越详细越好，不要生成多余的注释} \n" +
                "最终格式是:  【【【【【 前端代码【【【【【  分析结论 \n" +
                "分析需求:分析一下用户趋势 原始数据如下: 用户id,用户数据,用户增量\n" +
                "1,200,10\n" +
                "2,200,20\n" +
                "3,800,10\n" +
                "4,90,30\n" +
                "5,800,10\n" +
                "9,800,20\n" +
                "7,800,10生成图标的类型是: 折线图"));


        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(SparkApiVersion.V2_0)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String content = chatResponse.getContent();
        System.out.println(content);
        String genChart0 = content.split("【【【【【")[0].trim();
        String genChart = content.split("【【【【【")[1].trim();
        String genResult = content.split("【【【【【")[2].trim();
        System.out.println("genChart0 = " + genChart0);
        System.out.println("genResult = " + genResult);
        System.out.println("genChart = " + genChart);
        // String pattern = "\\s*\\{[\\s\\S]+?\\}";
        // // 使用了 Pattern.DOTALL 选项来匹配包括换行符在内的所有字符
        // Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        // Matcher matcher = r.matcher(content);
        // // 获取匹配到的内容
        // if (matcher.find()) {
        //     matcher.group(0);
        //     matcher.group(1);
        //     matcher.group(2);
        // }
        // String genChart = matcher.find() ? matcher.group(0) : "";

    }
}