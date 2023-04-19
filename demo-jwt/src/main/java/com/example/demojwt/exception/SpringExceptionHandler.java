package com.example.demojwt.exception;

import com.example.demojwt.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: SYA
 * @Date: 2023/4/19
 * @Version: V1.0
 * @Description:
 */
@RestControllerAdvice
@Slf4j
class SpringExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

}


