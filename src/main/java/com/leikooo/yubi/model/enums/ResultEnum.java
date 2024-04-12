package com.leikooo.yubi.model.enums;

import lombok.Getter;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @date 2024/4/12
 * @description
 */
@Getter
public enum ResultEnum {
    WAIT("wait"),
    RUNNING("running"),
    SUCCEED("succeed"),
    FAILED("failed");

    private final String des;

    ResultEnum(String des) {
        this.des = des;
    }
}
