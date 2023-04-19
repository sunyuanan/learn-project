package com.example.demojwt.interceptor;


import com.example.demojwt.util.JwtUtil;
import com.example.demojwt.util.TokenContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SYA
 * @Date 2023/4/14 10:53
 * @Description:
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        List<String> filtrationList = getFiltrationList();

        //地址过滤
        String uri = request.getRequestURI();
        if (filtrationList.contains(uri)) {
            return true;
        }
        //Token 验证
        String token = request.getHeader(JwtUtil.getHeader());
        //验证token
        JwtUtil.verify(token);

        TokenContext.setToken(token);

        return true;
    }


    /**
     * 完成后
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @param ex       Exception
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenContext.remove();
    }


    /**
     * 获取过滤列表
     *
     * @return {@code List<String>}
     */
    protected List<String> getFiltrationList() {
        List<String> filtrationList = new ArrayList<>();
        filtrationList.add("/login");

        return filtrationList;
    }


}