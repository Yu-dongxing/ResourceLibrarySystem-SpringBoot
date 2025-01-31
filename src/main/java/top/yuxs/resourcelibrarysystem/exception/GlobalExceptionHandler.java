package top.yuxs.resourcelibrarysystem.exception;
import top.yuxs.resourcelibrarysystem.pojo.Result;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@RestControllerAdvice
//全局异常处理器
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleCorsException(HttpRequestMethodNotSupportedException e) {
        return Result.error("不支持的请求方法: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage() : "操作失败");
    }
}
