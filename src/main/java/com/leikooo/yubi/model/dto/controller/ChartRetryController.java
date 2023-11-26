package com.leikooo.yubi.model.dto.controller;

import com.leikooo.yubi.model.entity.User;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author leikooo
 * @Description
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChartRetryController implements Serializable {


    private static final long serialVersionUID = 2645307609377346713L;
    /**
     * chartId
     */
    private Long chartId;

    /**
     * 登录的用户
     */
    private User loginUser;

    public Long getLoginUserId() {
        return loginUser.getId();
    }
}
