package com.leikooo.yubi.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author leikooo
 * @Description
 */
@Slf4j
public class ExcelUtils {

    public static String getExcelFileName(final MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            List<Map<Integer, String>> excelData = EasyExcel.read(inputStream).excelType(ExcelTypeEnum.XLSX).sheet().headRowNumber(0).doReadSync();
            // 读取表头 LinkedHashMap 是按照顺序读取的
            LinkedHashMap<Integer, String> headerMap = (LinkedHashMap<Integer, String>) excelData.get(0);
            List<String> headerList = headerMap.values().stream()
                    .filter(ObjectUtils::isNotEmpty)
                    .collect(Collectors.toList());
            // 读取数据
            List<String> dataLines = excelData.stream()
                    .skip(1) // 跳过表头
                    .map(dataMap -> dataMap.values().stream()
                            .filter(ObjectUtils::isNotEmpty)
                            .collect(Collectors.joining(","))) // 拼接数据字段
                    .collect(Collectors.toList());
            // 使用 StringJoiner 来构建 CSV 字符串
            StringJoiner csvContent = new StringJoiner("\n");
            csvContent.add(String.join(",", headerList)); // 添加表头
            dataLines.forEach(csvContent::add); // 添加数据行
            // System.out.println(csvContent);
            return csvContent.toString();
        } catch (Exception e) {
            log.error("读取excel文件失败: ", e);
            throw new RuntimeException(e);
        }
    }
}
