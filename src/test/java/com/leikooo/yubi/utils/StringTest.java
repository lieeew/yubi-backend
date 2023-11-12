package com.leikooo.yubi.utils;


import com.leikooo.yubi.mapper.ChartMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leikooo
 * @Description
 */
@SpringBootTest
@Slf4j
public class StringTest {

    @Resource
    private ChartMapper chartMapper;

    @Test
    void test() {
        // 用户id,用户数据
        // 1,9090
        // 2,200
        // 3,800
        // 4,90
        // 5,800
        String data = "用户id,用户数据\n" +
                "1,9090\n" +
                "2,200\n" +
                "3,800\n" +
                "4,90";
        String[] columns = data.split("\n");
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO chars_" + "1723184096625020929" + " VALUES ");
        for (int i = 1; i < columns.length; i++) {
            sb.append("(").append(columns[i]).append(")");
            if (i != columns.length - 1) {
                sb.append(", ");
            }
        }
        System.out.println(sb);
        chartMapper.insertValue(sb.toString());
    }

    @Test
    void test2() {
        String s = "```javascript\n" +
                "option = {\n" +
                "  radar: {\n" +
                "    name: '用户趋势',\n" +
                "    indicator: [\n" +
                "      { name: '用户数据', max: 1000 },\n" +
                "      { name: '用户增量', max: 100 }\n" +
                "    ]\n" +
                "  },\n" +
                "  series: [\n" +
                "    {\n" +
                "      name: '用户',\n" +
                "      type: 'radar',\n" +
                "      data: [\n" +
                "        [200, 10],\n" +
                "        [200, 20],\n" +
                "        [800, 10],\n" +
                "        [90, 30],\n" +
                "        [800, 10],\n" +
                "        [800, 20],\n" +
                "        [800, 10]\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "};```";
        // 使用正则表达式匹配 JavaScript  内的内容
        String pattern = "```javascript\\s*(.*?)\\s*```";
        // 使用了 Pattern.DOTALL 选项来匹配包括换行符在内的所有字符
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = r.matcher(s);
        // 获取匹配到的内容
        if (matcher.find()) {
            s = matcher.group(1);
        }
    }

    @Test
    void test3() {
        List<Map<String, Object>> maps = chartMapper.queryChartData("chars_1723299860338864129");
        System.out.println("maps = " + maps);
    }
}
