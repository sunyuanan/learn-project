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

/**
 * @author NDIOT-10
 * @Date 2023/4/14 10:53
 * @Description:
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //地址过滤
        String uri = request.getRequestURI();
        if (uri.contains("/login")) {
            return true;
        }
        //Token 验证
        String token = request.getHeader(JwtUtil.getHeader());
        if (!StringUtils.hasText(token)) {
            token = request.getParameter(JwtUtil.getHeader());
        }
        if (!StringUtils.hasText(token)) {
            throw new Exception(JwtUtil.getHeader() + "不能为空");
        }

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

}