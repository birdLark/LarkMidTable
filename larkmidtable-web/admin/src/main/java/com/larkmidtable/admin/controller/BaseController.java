package com.larkmidtable.admin.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.larkmidtable.admin.util.JwtTokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static com.larkmidtable.core.util.Constants.STRING_BLANK;

/**
 *
 * @Author: LarkMidTable
 * @Date: 2020/9/16 11:14
 * @Description: 基础建设-用户管理
 *
 **/
public class BaseController extends ApiController {

    public Integer getCurrentUserId(HttpServletRequest request) {
        Enumeration<String> auth = request.getHeaders(JwtTokenUtils.TOKEN_HEADER);
        String token = auth.nextElement().replace(JwtTokenUtils.TOKEN_PREFIX, STRING_BLANK);
        return JwtTokenUtils.getUserId(token);
    }
}
