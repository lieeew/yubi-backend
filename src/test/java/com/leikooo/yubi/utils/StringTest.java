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
        String s = "【【【【【\n" +
                "```javascript\n" +
                "option = {\n" +
                "    xAxis: {\n" +
                "        type: 'category',\n" +
                "        data: ['用户1', '用户2', '用户3', '用户4', '用户5', '用户6', '用户7']\n" +
                "    },\n" +
                "    yAxis: {\n" +
                "        type: 'value'\n" +
                "    },\n" +
                "    series: [{\n" +
                "        name: '用户数据',\n" +
                "        type: 'line',\n" +
                "        data: [200, 200, 800, 90, 800, 800, 800]\n" +
                "    }, {\n" +
                "        name: '用户增量',\n" +
                "        type: 'line',\n" +
                "        data: [10, 20, 10, 30, 10, 20, 10]\n" +
                "    }]\n" +
                "};\n" +
                "```\n" +
                "】】】】】";
        String pattern = "```javascrip\\s*(.*?)\\s*````";
        // 使用了 Pattern.DOTALL 选项来匹配包括换行符在内的所有字符
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = r.matcher(s);
        if (matcher.find()) {
            String group1 = matcher.group(0);
            System.out.println("group1 = " + group1);
            String group2 = matcher.group(1);
            System.out.println("group2 = " + group2);
            String group3 = matcher.group(2);
            System.out.println("group3 = " + group3);
            String group4 = matcher.group(3);
            System.out.println("group4 = " + group4);
        }
    }

    @Test
    void test3() {
        List<Map<String, Object>> maps = chartMapper.queryChartData(1722610390535233537L);
        System.out.println("maps = " + maps);
    }

    @Test
    void test4() {
        String s = "【【【【【\n" +
                "```javascript\n" +
                "option = {\n" +
                "    xAxis: {\n" +
                "        type: 'category',\n" +
                "        data: ['用户1', '用户2', '用户3', '用户4', '用户5', '用户6', '用户7']\n" +
                "    },\n" +
                "    yAxis: {\n" +
                "        type: 'value'\n" +
                "    },\n" +
                "    series: [{\n" +
                "        name: '用户数据',\n" +
                "        type: 'line',\n" +
                "        data: [200, 200, 800, 90, 800, 800, 800]\n" +
                "    }, {\n" +
                "        name: '用户增量',\n" +
                "        type: 'line',\n" +
                "        data: [10, 20, 10, 30, 10, 20, 10]\n" +
                "    }]\n" +
                "};\n" +
                "```\n" +
                "】】】】】";
        // option\\s*=\\s*\\{[\\s\\S]+?\\};
        String pattern = "\\s*\\{[\\s\\S]+?\\};";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = r.matcher(s);
        if (matcher.find()) {
            String optionString = matcher.group(0);
            System.out.println(optionString);
        } else {
            System.out.println("No match found.");
        }
    }
}
