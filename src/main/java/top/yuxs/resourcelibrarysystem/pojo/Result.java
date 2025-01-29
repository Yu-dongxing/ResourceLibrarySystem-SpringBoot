package top.yuxs.resourcelibrarysystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code ;      //
    private String message;
    private T data;

    public static <E> Result <E> success(E data){
        return new Result<>(0,"操作成功",data);
    }
    public static Result success(){
        return new Result(0,"操作成功",null);
    }
    public static Result error(String message ){
        return new Result(1,message,null);
    }

}
//public class Result<T> {
//    private Integer code;
//    private String message;
//    private T data;
//
//    public static <T> Result<T> success(T data) {
//        Result<T> result = new Result<>();
//        result.setCode(0);
//        result.setMessage("操作成功");
//        result.setData(data);
//        return result;
//    }
//
//    public static <T> Result<T> success() {
//        Result<T> result = new Result<>();
//        result.setCode(0);
//        result.setMessage("操作成功");
//        result.setData(null);
//        return result;
//    }
//
//    public static <T> Result<T> error(String message) {
//        Result<T> result = new Result<>();
//        result.setCode(1);
//        result.setMessage(message);
//        result.setData(null);
//        return result;
//    }
//
//    // Getters and Setters
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//}
