package com.szx.common.exception;

import com.szx.common.result.R;
import com.szx.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description TODO
 * @Author 沙政鑫
 * @Data 2021/6/27  19:56
 */

@Slf4j
@RestControllerAdvice

public class UnifiedExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e){
        log.error(e.getMessage(),e);
        return R.error();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R handleException(BadSqlGrammarException e){
        log.error(e.getMessage(),e);
        return R.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public R handleException(BusinessException e){
        log.error(e.getMessage(),e);
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
