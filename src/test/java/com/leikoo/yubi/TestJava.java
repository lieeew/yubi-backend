package com.leikoo.yubi;

import com.leikooo.yubi.MainApplication;
import com.leikooo.yubi.manager.AIManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @date 2024/9/12
 * @description
 */
@SpringBootTest(classes = MainApplication.class)
public class TestJava {
    @Resource
    private AIManager ai2Manager;


    @Test
    public void test() {
        String c = "分析需求：\n" +
                "分析网站用户的增长情况 \n" +
                "请使用 柱状图 \n" +
                "原始数据：\n" +
                "日期,用户数\n" +
                "1号,10\n" +
                " 2号,20\n" +
                " 3号,30";
        String s = ai2Manager.sendMsgToXingHuo(true, c);
        System.out.println("s = " + s);
    }
}
